/* == SIARDexcerpt ==============================================================================
 * The SIARDexcerpt application is used for excerpt a record from a SIARD-File. Copyright (C) 2016
 * Claire Röthlisberger (KOST-CECO)
 * -----------------------------------------------------------------------------------------------
 * SIARDexcerpt is a development of the KOST-CECO. All rights rest with the KOST-CECO. This
 * application is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. This application is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the follow GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA or see <http://www.gnu.org/licenses/>.
 * ============================================================================================== */

package ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.impl;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.jdom2.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
// import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptCGrepException;
import ch.kostceco.tools.siardexcerpt.excerption.ValidationModuleImpl;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptCGrepModule;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.util.StreamGobbler;
import ch.kostceco.tools.siardexcerpt.util.Util;

/** Besteht eine korrekte primäre Verzeichnisstruktur: /header/metadata.xml sowie
 * /header/metadata.xsd und /content */
public class ExcerptCGrepModuleImpl extends ValidationModuleImpl implements ExcerptCGrepModule
{

	private ConfigurationService	configurationService;

	public static String					NEWLINE	= System.getProperty( "line.separator" );

	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService( ConfigurationService configurationService )
	{
		this.configurationService = configurationService;
	}

	@Override
	public boolean validate( File siardDatei, File outFile, String excerptString )
			throws ExcerptCGrepException
	{

		boolean isValid = true;

		File fGrepExe = new File( "resources" + File.separator + "grep" + File.separator + "grep.exe" );
		String pathToGrepExe = fGrepExe.getAbsolutePath();
		if ( !fGrepExe.exists() ) {
			// grep.exe existiert nicht --> Abbruch
			getMessageService().logError(
					getTextResourceService().getText( MESSAGE_XML_MODUL_C )
							+ getTextResourceService().getText( MESSAGE_XML_C_MISSINGFILE,
									fGrepExe.getAbsolutePath() ) );
			return false;
		} else {
			File fMsys10dll = new File( "resources" + File.separator + "grep" + File.separator
					+ "msys-1.0.dll" );
			if ( !fMsys10dll.exists() ) {
				// msys-1.0.dll existiert nicht --> Abbruch
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_MODUL_C )
								+ getTextResourceService().getText( MESSAGE_XML_C_MISSINGFILE,
										fMsys10dll.getAbsolutePath() ) );
				return false;
			}
		}

		File tempOutFile = new File( outFile.getAbsolutePath() + ".tmp" );
		String content = "";

		// Record aus Maintable herausholen
		try {
			if ( tempOutFile.exists() ) {
				Util.deleteDir( tempOutFile );
			}

			/* Nicht vergessen in "src/main/resources/config/applicationContext-services.xml" beim
			 * entsprechenden Modul die property anzugeben: <property name="configurationService"
			 * ref="configurationService" /> */

			String name = getConfigurationService().getMaintableName();
			String folder = getConfigurationService().getMaintableFolder();
			String cell = getConfigurationService().getMaintablePrimarykeyCell();

			File fMaintable = new File( siardDatei.getAbsolutePath() + File.separator + "content"
					+ File.separator + "schema0" + File.separator + folder + File.separator + folder + ".xml" );

			try {
				// grep "<c11>7561234567890</c11>" table13.xml >> output.txt
				String command = "cmd /c \"" + pathToGrepExe + " \"<" + cell + ">" + excerptString + "</"
						+ cell + ">\" " + fMaintable.getAbsolutePath() + " >> " + tempOutFile.getAbsolutePath()
						+ "\"";
				/* Das redirect Zeichen verunmöglicht eine direkte eingabe. mit dem geschachtellten Befehl
				 * gehts: cmd /c\"urspruenlicher Befehl\" */

				System.out.println( command );

				Process proc = null;
				Runtime rt = null;

				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_ELEMENT_OPEN, name ) );

				try {
					Util.switchOffConsole();
					rt = Runtime.getRuntime();
					proc = rt.exec( command.toString().split( " " ) );
					// .split(" ") ist notwendig wenn in einem Pfad ein Doppelleerschlag vorhanden ist!

					// Fehleroutput holen
					StreamGobbler errorGobbler = new StreamGobbler( proc.getErrorStream(), "ERROR" );

					// Output holen
					StreamGobbler outputGobbler = new StreamGobbler( proc.getInputStream(), "OUTPUT" );

					// Threads starten
					errorGobbler.start();
					outputGobbler.start();

					// Warte, bis wget fertig ist
					proc.waitFor();

					Util.switchOnConsole();

				} catch ( Exception e ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_C )
									+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
					return false;
				} finally {
					if ( proc != null ) {
						closeQuietly( proc.getOutputStream() );
						closeQuietly( proc.getInputStream() );
						closeQuietly( proc.getErrorStream() );
					}
				}

				Scanner scanner = new Scanner( tempOutFile );
				content = "";
				content = scanner.useDelimiter( "\\Z" ).next();
				scanner.close();

				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_ELEMENT_CONTENT, content ) );
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_ELEMENT_CLOSE, name ) );

				if ( tempOutFile.exists() ) {
					Util.deleteDir( tempOutFile );
				}
				content = "";

				// Ende Grep

			} catch ( Exception e ) {
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_MODUL_C )
								+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
				return false;
			}

		} catch ( Exception e ) {
			getMessageService().logError(
					getTextResourceService().getText( MESSAGE_XML_MODUL_C )
							+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}

		// Ende MainTable

		// TODO: grep der SubTables
		try {
			String name = null;
			String folder = null;
			String cell = null;

			InputStream fin = new FileInputStream( new File( "configuration" + File.separator
					+ "SIARDexcerpt.conf.xml" ) );
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build( fin );
			fin.close();

			/* read the document and for each subTable */
			Namespace ns = Namespace.getNamespace( "" );

			// select schema elements and loop
			List<Element> subtables = document.getRootElement().getChild( "subtables", ns )
					.getChildren( "subtable", ns );
			for ( Element subtable : subtables ) {
				name = subtable.getChild( "name", ns ).getText();
				folder = subtable.getChild( "folder", ns ).getText();
				cell = subtable.getChild( "foreignkeycell", ns ).getText();

				System.out.println( name + " - " + folder + " - " + cell );
				File fSubtable = new File( siardDatei.getAbsolutePath() + File.separator + "content"
						+ File.separator + "schema0" + File.separator + folder + File.separator + folder
						+ ".xml" );

				try {
					// grep "<c11>7561234567890</c11>" table13.xml >> output.txt
					String command = "cmd /c \"" + pathToGrepExe + " \"<" + cell + ">" + excerptString + "</"
							+ cell + ">\" " + fSubtable.getAbsolutePath() + " >> "
							+ tempOutFile.getAbsolutePath() + "\"";
					/* Das redirect Zeichen verunmöglicht eine direkte eingabe. mit dem geschachtellten Befehl
					 * gehts: cmd /c\"urspruenlicher Befehl\" */

					System.out.println( command );

					Process proc = null;
					Runtime rt = null;

					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_ELEMENT_OPEN, name ) );

					try {
						Util.switchOffConsole();
						rt = Runtime.getRuntime();
						proc = rt.exec( command.toString().split( " " ) );
						// .split(" ") ist notwendig wenn in einem Pfad ein Doppelleerschlag vorhanden ist!

						// Fehleroutput holen
						StreamGobbler errorGobbler = new StreamGobbler( proc.getErrorStream(), "ERROR" );

						// Output holen
						StreamGobbler outputGobbler = new StreamGobbler( proc.getInputStream(), "OUTPUT" );

						// Threads starten
						errorGobbler.start();
						outputGobbler.start();

						// Warte, bis wget fertig ist
						proc.waitFor();

						Util.switchOnConsole();

					} catch ( Exception e ) {
						getMessageService().logError(
								getTextResourceService().getText( MESSAGE_XML_MODUL_C )
										+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
						return false;
					} finally {
						if ( proc != null ) {
							closeQuietly( proc.getOutputStream() );
							closeQuietly( proc.getInputStream() );
							closeQuietly( proc.getErrorStream() );
						}
					}

					Scanner scanner = new Scanner( tempOutFile );
					content = "";
					try {
						content = scanner.useDelimiter( "\\Z" ).next();
					} catch ( Exception e ) {
						// Grep ergab kein treffer Content Null
						content = "";
					}
					scanner.close();

					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_ELEMENT_CONTENT, content ) );
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_ELEMENT_CLOSE, name ) );

					if ( tempOutFile.exists() ) {
						Util.deleteDir( tempOutFile );
					}
					content = "";

					// Ende Grep

				} catch ( Exception e ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_C )
									+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
					return false;
				}

				// Ende SubTables

			}
		} catch ( Exception e ) {
			getMessageService().logError(
					getTextResourceService().getText( MESSAGE_XML_MODUL_C )
							+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}

		return isValid;
	}
}
