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

import java.io.File;
import java.util.Scanner;

import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptBSearchException;
import ch.kostceco.tools.siardexcerpt.excerption.ValidationModuleImpl;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptBSearchModule;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.util.StreamGobbler;
import ch.kostceco.tools.siardexcerpt.util.Util;

/** Besteht eine korrekte primäre Verzeichnisstruktur: /header/metadata.xml sowie
 * /header/metadata.xsd und /content */
public class ExcerptBSearchModuleImpl extends ValidationModuleImpl implements ExcerptBSearchModule
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
	public boolean validate( File siardDatei, File outFileSearch, String searchString )
			throws ExcerptBSearchException
	{
		boolean isValid = true;

		File fGrepExe = new File( "resources" + File.separator + "grep" + File.separator + "grep.exe" );
		String pathToGrepExe = fGrepExe.getAbsolutePath();
		if ( !fGrepExe.exists() ) {
			// grep.exe existiert nicht --> Abbruch
			getMessageService().logError(
					getTextResourceService().getText( MESSAGE_XML_MODUL_B )
							+ getTextResourceService().getText( ERROR_XML_C_MISSINGFILE,
									fGrepExe.getAbsolutePath() ) );
			return false;
		} else {
			File fMsys10dll = new File( "resources" + File.separator + "grep" + File.separator
					+ "msys-1.0.dll" );
			if ( !fMsys10dll.exists() ) {
				// msys-1.0.dll existiert nicht --> Abbruch
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_MODUL_B )
								+ getTextResourceService().getText( ERROR_XML_C_MISSINGFILE,
										fMsys10dll.getAbsolutePath() ) );
				return false;
			}
		}

		File tempOutFile = new File( outFileSearch.getAbsolutePath() + ".tmp" );
		String content = "";
		String contentAll = "";

		// Records aus table herausholen
		try {
			if ( tempOutFile.exists() ) {
				Util.deleteDir( tempOutFile );
			}

			/* Nicht vergessen in "src/main/resources/config/applicationContext-services.xml" beim
			 * entsprechenden Modul die property anzugeben: <property name="configurationService"
			 * ref="configurationService" /> */

//			String name = getConfigurationService().getSearchtableName();
			String name = "siardexcerptsearch";
			String folder = getConfigurationService().getSearchtableFolder();

			File fSearchtable = new File( siardDatei.getAbsolutePath() + File.separator + "content"
					+ File.separator + "schema0" + File.separator + folder + File.separator + folder + ".xml" );

			searchString = searchString.replaceAll( "\\.", "\\.*" );

			try {
				// grep -E "REGEX-Suchbegriff" table13.xml >> output.txt
				String command = "cmd /c \"" + pathToGrepExe + " -E \"" + searchString + "\" "
						+ fSearchtable.getAbsolutePath() + " >> " + tempOutFile.getAbsolutePath() + "\"";
				/* Das redirect Zeichen verunmöglicht eine direkte eingabe. mit dem geschachtellten Befehl
				 * gehts: cmd /c\"urspruenlicher Befehl\" */

				// System.out.println( command );

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
							getTextResourceService().getText( MESSAGE_XML_MODUL_B )
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
				contentAll = "";
				content = "";
				contentAll = scanner.useDelimiter( "\\Z" ).next();
				scanner.close();
				content = contentAll;
				/* im contentAll ist jetzt der Gesamtstring, dieser soll anschliessend nur noch aus den 4
				 * Such-Zellen und den weiteren 4 ResultateZellen bestehen -> content */
				String nr0 = getConfigurationService().getcellNumberkey();
				String nr1 = getConfigurationService().getcellNumber1();
				String nr2 = getConfigurationService().getcellNumber2();
				String nr3 = getConfigurationService().getcellNumber3();
				String nr4 = getConfigurationService().getcellNumber4();
				String nr5 = getConfigurationService().getcellNumber5();
				String nr6 = getConfigurationService().getcellNumber6();
				String nr7 = getConfigurationService().getcellNumber7();

				String cellLoop = "";
				String modifString = "";
				// Loop von 1, 2, 3 ... bis 499999.
				for ( int i = 1; i < 500000; i++ ) {
					cellLoop = "";
					cellLoop = "c" + i;
					if ( cellLoop.equals( nr0 ) || cellLoop.equals( nr1 ) || cellLoop.equals( nr2 ) || cellLoop.equals( nr3 )
							|| cellLoop.equals( nr4 ) || cellLoop.equals( nr5 ) || cellLoop.equals( nr6 )
							|| cellLoop.equals( nr7 ) ) {
						// wird behalten
						 modifString = "c" + i + ">";

						if ( cellLoop.equals( nr0 ) ) {
							content = content.replaceAll( modifString, "col0>" );
						} else {
							if ( cellLoop.equals( nr1 ) ) {
								content = content.replaceAll( modifString, "col1>" );
							} else {
								if ( cellLoop.equals( nr2 ) ) {
									content = content.replaceAll( modifString, "col2>" );
								} else {
									if ( cellLoop.equals( nr3 ) ) {
										content = content.replaceAll( modifString, "col3>" );
									} else {
										if ( cellLoop.equals( nr4 ) ) {
											content = content.replaceAll( modifString, "col4>" );
										} else {
											if ( cellLoop.equals( nr5 ) ) {
												content = content.replaceAll( modifString, "col5>" );
											} else {
												if ( cellLoop.equals( nr6 ) ) {
													content = content.replaceAll( modifString, "col6>" );
												} else {
													if ( cellLoop.equals( nr7 ) ) {
														content = content.replaceAll( modifString, "col7>" );
													} 
												}
											}
										}
									}
								}
							}
						}
					} else {
						String deletString = "<c" + i + ">" + ".*" + "</c" + i + ">";
						content = content.replaceAll( deletString, "" );
					}
				}

				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_ELEMENT_CONTENT, content ) );
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_ELEMENT_CLOSE, name ) );

				if ( tempOutFile.exists() ) {
					Util.deleteDir( tempOutFile );
				}
				contentAll = "";
				content = "";

				// Ende Grep

			} catch ( Exception e ) {
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_MODUL_B )
								+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
				return false;
			}

		} catch ( Exception e ) {
			getMessageService().logError(
					getTextResourceService().getText( MESSAGE_XML_MODUL_B )
							+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}

		return isValid;
	}
}
