/* == SIARDexcerpt ==============================================================================
 * The SIARDexcerpt v0.0.1 application is used for excerpt a record from a SIARD-File. Copyright (C)
 * 2016 Claire Röthlisberger (KOST-CECO)
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

package ch.kostceco.tools.siardexcerpt;

import java.io.File;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// import ch.kostceco.tools.siardexcerpt.controller.Controllersearch;
import ch.kostceco.tools.siardexcerpt.controller.Controllerexcerpt;
import ch.kostceco.tools.siardexcerpt.logging.LogConfigurator;
import ch.kostceco.tools.siardexcerpt.logging.Logger;
import ch.kostceco.tools.siardexcerpt.logging.MessageConstants;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.service.TextResourceService;
import ch.kostceco.tools.siardexcerpt.util.Util;

/** Dies ist die Starter-Klasse, verantwortlich für das Initialisieren des Controllers, des Loggings
 * und das Parsen der Start-Parameter.
 * 
 * @author Rc Claire Röthlisberger, KOST-CECO */

public class SIARDexcerpt implements MessageConstants
{

	private static final Logger		LOGGER	= new Logger( SIARDexcerpt.class );

	private TextResourceService		textResourceService;
	private ConfigurationService	configurationService;

	public TextResourceService getTextResourceService()
	{
		return textResourceService;
	}

	public void setTextResourceService( TextResourceService textResourceService )
	{
		this.textResourceService = textResourceService;
	}

	public ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	public void setConfigurationService( ConfigurationService configurationService )
	{
		this.configurationService = configurationService;
	}

	/** Die Eingabe besteht aus 3 Parameter: [0] Pfad zur SIARD-Datei oder Verzeichnis [1] Suchtext [2]
	 * configfile
	 * 
	 * @param args
	 * @throws IOException */

	public static void main( String[] args ) throws IOException
	{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:config/applicationContext.xml" );

		// Zeitstempel der Datenextraktion
		java.util.Date nowStart = new java.util.Date();
		java.text.SimpleDateFormat sdfStart = new java.text.SimpleDateFormat( "dd.MM.yyyy HH:mm:ss" );
		String ausgabeStart = sdfStart.format( nowStart );

		/* TODO: siehe Bemerkung im applicationContext-services.xml bezüglich Injection in der
		 * Superklasse aller Impl-Klassen ValidationModuleImpl validationModuleImpl =
		 * (ValidationModuleImpl) context.getBean("validationmoduleimpl"); */

		SIARDexcerpt siardexcerpt = (SIARDexcerpt) context.getBean( "siardexcerpt" );

		// Ueberprüfung des Parameters (Log-Verzeichnis)
		String pathToOutput = siardexcerpt.getConfigurationService().getPathToOutput();

		File directoryOfOutput = new File( pathToOutput );

		if ( !directoryOfOutput.exists() ) {
			directoryOfOutput.mkdir();
		}

		// Im Logverzeichnis besteht kein Schreibrecht
		if ( !directoryOfOutput.canWrite() ) {
			System.out.println( siardexcerpt.getTextResourceService().getText(
					ERROR_LOGDIRECTORY_NOTWRITABLE, directoryOfOutput ) );
			System.exit( 1 );
		}

		if ( !directoryOfOutput.isDirectory() ) {
			System.out.println( siardexcerpt.getTextResourceService().getText(
					ERROR_LOGDIRECTORY_NODIRECTORY ) );
			System.exit( 1 );
		}

		// Ist die Anzahl Parameter (3) korrekt?
		if ( args.length != 3 ) {
			System.out.println( siardexcerpt.getTextResourceService().getText( ERROR_PARAMETER_USAGE ) );
			System.exit( 1 );
		}

		/* TODO: arg 2 sollte den pfad zur configdatei angeben. Prov hartcodiert
		 * 
		 * String path = "configuration/TAXAR.conf.xml";
		 * 
		 * in ConfigurationServiceImpl */

		String excerptString = new String( args[1] );
		File siardDatei = new File( args[0] );
		File configFile = new File( args[2] );
		String outDateiName = siardDatei.getName() + "_" + excerptString + "_SIARDexcerpt.xml";

		// Informationen zum Arbeitsverzeichnis holen
		String pathToWorkDir = siardexcerpt.getConfigurationService().getPathToWorkDir();
		/* Nicht vergessen in "src/main/resources/config/applicationContext-services.xml" beim
		 * entsprechenden Modul die property anzugeben: <property name="configurationService"
		 * ref="configurationService" /> */

		// Informationen zum Archiv holen
		String archive = siardexcerpt.getConfigurationService().getArchive();

		// Konfiguration des Outputs, ein File Logger wird zusätzlich erstellt
		LogConfigurator logConfigurator = (LogConfigurator) context.getBean( "logconfigurator" );
		String outFileName = logConfigurator.configure( directoryOfOutput.getAbsolutePath(),
				outDateiName );
		File outFile = new File( outFileName );
		// Ab hier kann ins Output geschrieben werden...

		System.out.println( "SIARDexcerpt" );
		System.out.println( "" );

		// Informationen zum XSL holen
		String pathToXSL = siardexcerpt.getConfigurationService().getPathToXSL();

		File xslOrig = new File( pathToXSL );
		File xslCopy = new File( directoryOfOutput.getAbsolutePath() + File.separator
				+ xslOrig.getName() );
		if ( !xslCopy.exists() ) {
			Util.copyFile( xslOrig, xslCopy );
		}

		LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_HEADER,
				xslCopy.getName() ) );
		LOGGER.logError( siardexcerpt.getTextResourceService()
				.getText( MESSAGE_XML_START, ausgabeStart ) );
		LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_ARCHIVE, archive ) );
		LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_INFO ) );

		File tmpDir = new File( pathToWorkDir );

		/* bestehendes Workverzeichnis Abbruch wenn nicht leer, da am Schluss das Workverzeichnis
		 * gelöscht wird und entsprechend bestehende Dateien gelöscht werden können */
		if ( tmpDir.exists() ) {
			if ( tmpDir.isDirectory() ) {
				// Get list of file in the directory. When its length is not zero the folder is not empty.
				String[] files = tmpDir.list();
				if ( files.length > 0 ) {
					LOGGER.logError( siardexcerpt.getTextResourceService().getText(
							ERROR_IOE,
							siardexcerpt.getTextResourceService().getText( ERROR_WORKDIRECTORY_EXISTS,
									pathToWorkDir ) ) );
					System.out.println( siardexcerpt.getTextResourceService().getText(
							ERROR_WORKDIRECTORY_EXISTS, pathToWorkDir ) );
					System.exit( 1 );
				}
			}
		}

		// die Anwendung muss mindestens unter Java 6 laufen
		String javaRuntimeVersion = System.getProperty( "java.vm.version" );
		if ( javaRuntimeVersion.compareTo( "1.6.0" ) < 0 ) {
			LOGGER.logError( siardexcerpt.getTextResourceService().getText( ERROR_IOE,
					siardexcerpt.getTextResourceService().getText( ERROR_WRONG_JRE ) ) );
			System.out.println( siardexcerpt.getTextResourceService().getText( ERROR_WRONG_JRE ) );
			System.exit( 1 );
		}

		// bestehendes Workverzeichnis wieder anlegen
		if ( !tmpDir.exists() ) {
			tmpDir.mkdir();
		}

		// Im workverzeichnis besteht kein Schreibrecht
		if ( !tmpDir.canWrite() ) {
			LOGGER
					.logError( siardexcerpt.getTextResourceService().getText(
							ERROR_IOE,
							siardexcerpt.getTextResourceService().getText( ERROR_WORKDIRECTORY_NOTWRITABLE,
									tmpDir ) ) );
			System.out.println( siardexcerpt.getTextResourceService().getText(
					ERROR_WORKDIRECTORY_NOTWRITABLE, tmpDir ) );
			System.exit( 1 );
		}

		// Ueberprüfung des Parameters (Val-Datei): existiert die Datei?
		if ( siardDatei.exists() ) {
			Controllerexcerpt controllerexcerpt = (Controllerexcerpt) context
					.getBean( "controllerexcerpt" );
			boolean okA = false;
			boolean okB = false;
			boolean okOther = false;
			if ( !siardDatei.isDirectory() ) {
				/* SIARD-Datei ist eine Datei
				 * 
				 * Die Datei muss als erstes ins Workverzeichnis extrahiert werden. Dies erfolgt im Schritt
				 * A. */

				// TODO:
				// okA = controllerexcerpt.executeA( siardDatei, outFile, excerptString );
				okA = true;

				if ( !okA ) {
					// SIARD Datei konte nicht entpackt werden
					LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_MODUL_A ) );
					LOGGER.logError( siardexcerpt.getTextResourceService().getText(
							ERROR_XML_A_CANNOTEXTRACTZIP ) );
					LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_LOGEND ) );
					System.out.println( MESSAGE_XML_MODUL_A );
					System.out.println( ERROR_XML_A_CANNOTEXTRACTZIP );
					System.out.println( "" );

					// Löschen des Arbeitsverzeichnisses, falls eines angelegt wurde
					if ( tmpDir.exists() ) {
						Util.deleteDir( tmpDir );
					}
					// Fehler Extraktion --> invalide
					System.exit( 2 );
				}

			}

			/* SIARD-Datei entpackt oder Datei war bereits ein Verzeichnis.
			 * 
			 * Gerade bei grösseren SIARD-Dateien ist es sinnvoll an einer Stelle das ausgepackte SIARD
			 * zu haben, damit diese nicht immer noch extrahiert werden muss */
			okB = controllerexcerpt.executeB( siardDatei, outFile, excerptString );

			if ( !okB ) {
				// Struktur entspricht nicht einer SIARD-Datei
				LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_MODUL_B ) );
				LOGGER.logError( siardexcerpt.getTextResourceService().getText( ERROR_XML_B_STRUCTURE ) );
				LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_LOGEND ) );
				System.out.println( MESSAGE_XML_MODUL_B );
				System.out.println( ERROR_XML_B_STRUCTURE );
				System.out.println( "" );

				// Löschen des Arbeitsverzeichnisses, falls eines angelegt wurde
				if ( tmpDir.exists() ) {
					Util.deleteDir( tmpDir );
				}
				// Fehler Extraktion --> invalide
				System.exit( 2 );
			} else {
				// Struktur sieht plausibel aus, extraktion kann starten
				okOther = controllerexcerpt.executeOther( siardDatei, outFile, excerptString );

				if ( !okOther ) {
					// Record konnte nicht extrahiert werden
					LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_MODUL_A ) );
					LOGGER.logError( siardexcerpt.getTextResourceService().getText(
							ERROR_XML_A_CANNOTEXTRACTZIP ) );
					LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_LOGEND ) );
					System.out.println( MESSAGE_XML_MODUL_A );
					System.out.println( ERROR_XML_A_CANNOTEXTRACTZIP );
					System.out.println( "" );

					// Löschen des Arbeitsverzeichnisses, falls eines angelegt wurde
					if ( tmpDir.exists() ) {
						Util.deleteDir( tmpDir );
					}
					// Fehler Extraktion --> invalide
					System.exit( 2 );
				} else {
					LOGGER.logError( siardexcerpt.getTextResourceService().getText( MESSAGE_XML_LOGEND ) );
					// Löschen des Arbeitsverzeichnisses, falls eines angelegt wurde
					if ( tmpDir.exists() ) {
						Util.deleteDir( tmpDir );
					}
					// Record konnte extrahiert werden
					System.exit( 0 );

				}

			}

		} else {
			// SIARD-Datei existiert nicht
			LOGGER.logError( siardexcerpt.getTextResourceService().getText( ERROR_IOE,
					siardexcerpt.getTextResourceService().getText( ERROR_SIARDFILE_FILENOTEXISTING ) ) );
			System.out.println( siardexcerpt.getTextResourceService().getText(
					ERROR_SIARDFILE_FILENOTEXISTING ) );
			System.exit( 1 );

		}
	}
}
