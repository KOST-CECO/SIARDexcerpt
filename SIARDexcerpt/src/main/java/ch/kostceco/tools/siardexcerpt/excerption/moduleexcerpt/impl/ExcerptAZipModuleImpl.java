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

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import ch.enterag.utils.zip.EntryInputStream;
import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;
import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptAZipException;
import ch.kostceco.tools.siardexcerpt.excerption.ValidationModuleImpl;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptAZipModule;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.util.Util;

/** SIARD-Datei entpacken */
public class ExcerptAZipModuleImpl extends ValidationModuleImpl implements ExcerptAZipModule
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
	public boolean validate( File siardDatei, File siardDateiNew, String noString )
			throws ExcerptAZipException
	{
		// Ausgabe -> Ersichtlich das SIARDexcerpt arbeitet
		int onWork = 41;

		boolean result = true;

		String toplevelDir = siardDatei.getName();
		int lastDotIdx = toplevelDir.lastIndexOf( "." );
		toplevelDir = toplevelDir.substring( 0, lastDotIdx );

		try {
			/* Nicht vergessen in "src/main/resources/config/applicationContext-services.xml" beim
			 * entsprechenden Modul die property anzugeben: <property name="configurationService"
			 * ref="configurationService" /> */
			// Arbeitsverzeichnis zum Entpacken des Archivs erstellen
			if ( siardDateiNew.exists() ) {
				Util.deleteDir( siardDateiNew );
			}
			if ( !siardDateiNew.exists() ) {
				siardDateiNew.mkdir();
			}

			/* Das metadata.xml und sein xsd müssen in das Filesystem extrahiert werden, weil bei bei
			 * Verwendung eines Inputstreams bei der Validierung ein Problem mit den xs:include Statements
			 * besteht, die includes können so nicht aufgelöst werden. Es werden hier jedoch nicht nur
			 * diese Files extrahiert, sondern gleich die ganze Zip-Datei, weil auch spätere Validierungen
			 * nur mit den extrahierten Files arbeiten können. */
			Zip64File zipfile = new Zip64File( siardDatei );
			List<FileEntry> fileEntryList = zipfile.getListFileEntries();
			for ( FileEntry fileEntry : fileEntryList ) {
				if ( !fileEntry.isDirectory() ) {
					byte[] buffer = new byte[8192];
					// Scheibe die Datei an den richtigen Ort respektive in den richtigen Ordner der ggf
					// angelegt werden muss.
					EntryInputStream eis = zipfile.openEntryInputStream( fileEntry.getName() );
					File newFile = new File( siardDateiNew, fileEntry.getName() );
					File parent = newFile.getParentFile();
					if ( !parent.exists() ) {
						parent.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream( newFile );
					for ( int iRead = eis.read( buffer ); iRead >= 0; iRead = eis.read( buffer ) ) {
						fos.write( buffer, 0, iRead );
					}
					eis.close();
					fos.close();
				} else {
					/* Scheibe den Ordner wenn noch nicht vorhanden an den richtigen Ort respektive in den
					 * richtigen Ordner der ggf angelegt werden muss. Dies muss gemacht werden, damit auch
					 * leere Ordner ins Work geschrieben werden. Diese werden danach im J als Fehler angegeben */
					EntryInputStream eis = zipfile.openEntryInputStream( fileEntry.getName() );
					File newFolder = new File( siardDateiNew, fileEntry.getName() );
					if ( !newFolder.exists() ) {
						File parent = newFolder.getParentFile();
						if ( !parent.exists() ) {
							parent.mkdirs();
						}
						newFolder.mkdirs();
					}
					eis.close();
				}

				if ( onWork == 41 ) {
					onWork = 2;
					System.out.print( "-   " );
					System.out.print( "\r" );
				} else if ( onWork == 11 ) {
					onWork = 12;
					System.out.print( "\\   " );
					System.out.print( "\r" );
				} else if ( onWork == 21 ) {
					onWork = 22;
					System.out.print( "|   " );
					System.out.print( "\r" );
				} else if ( onWork == 31 ) {
					onWork = 32;
					System.out.print( "/   " );
					System.out.print( "\r" );
				} else {
					onWork = onWork + 1;
				}
			}
			System.out.print( "   " );
			System.out.print( "\r" );

			zipfile.close();
		} catch ( Exception e ) {
			getMessageService().logError(
					getTextResourceService().getText( MESSAGE_XML_MODUL_A )
							+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}
		return result;
	}
}
