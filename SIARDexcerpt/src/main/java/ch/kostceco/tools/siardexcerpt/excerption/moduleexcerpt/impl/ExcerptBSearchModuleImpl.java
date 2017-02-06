/* == SIARDexcerpt ==============================================================================
 * The SIARDexcerpt application is used for excerpt a record from a SIARD-File. Copyright (C) 2016-2017
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
import java.io.FileInputStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptBSearchException;
import ch.kostceco.tools.siardexcerpt.excerption.ValidationModuleImpl;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptBSearchModule;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.util.StreamGobbler;
import ch.kostceco.tools.siardexcerpt.util.Util;

/** 2) search: gemäss config die Tabelle mit Suchtext befragen und Ausgabe des Resultates */
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
		boolean time = false;
		// Zeitstempel für Performance tests wird nur ausgegeben wenn time = true
		java.util.Date nowTime = new java.util.Date();
		java.text.SimpleDateFormat sdfStartS = new java.text.SimpleDateFormat( "dd.MM.yyyy HH:mm:ss" );
		String stringNowTime = sdfStartS.format( nowTime );

		if ( time ) {
			nowTime = new java.util.Date();
			stringNowTime = sdfStartS.format( nowTime );
			System.out.println( stringNowTime + " Start der Suche" );
		}
		// Schema herausfinden
		File fSchema = new File( siardDatei.getAbsolutePath() + File.separator + "content"
				+ File.separator + "schema0" );
		for ( int s = 0; s < 9999999; s++ ) {
			fSchema = new File( siardDatei.getAbsolutePath() + File.separator + "content"
					+ File.separator + "schema" + s );
			if ( fSchema.exists() ) {
				break;
			}
		}

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

			// String name = getConfigurationService().getSearchtableName();
			String name = "siardexcerptsearch";
			String folder = getConfigurationService().getMaintableFolder();
			if ( folder.startsWith( "Configuration-Error:" ) ) {
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + folder );
				return false;
			}
			String insensitiveOption = "";
			String insensitive = getConfigurationService().getInsensitive();
			if ( insensitive.startsWith( "Configuration-Error:" ) ) {
				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + insensitive );
				return false;
			} else if ( insensitive.equalsIgnoreCase( "yes" ) ) {
				insensitiveOption = "i";
			}

			File fSearchtable = new File( fSchema.getAbsolutePath() + File.separator + folder
					+ File.separator + folder + ".xml" );
			// Bringt die ganze <row>...</row> auf eine Zeile
			for ( int r = 0; r < 20; r++ ) {
				Util.oldnewstring( " <", "<", fSearchtable );
			}
			Util.oldnewstring( System.getProperty( "line.separator" ) + "<c", "<c", fSearchtable );
			Util.oldnewstring( System.getProperty( "line.separator" ) + "</row", "</row", fSearchtable );

			// Trennt ><row>. Nur eine row auf eine neue Zeile
			Util.oldnewstring( "><row", ">" + System.getProperty( "line.separator" ) + "<row",
					fSearchtable );

			/* Der SearchString soll nur über <row>...</row> durchgeführt werden
			 * 
			 * einmal mit row erweitern */
			searchString = "row." + searchString;

			/* Der SearchString kann Leerschläge enthalten, welche bei grep Problem verursachen.
			 * Entsprechend werden diese durch . ersetzt (Wildcard) */
			searchString = searchString.replaceAll( " ", "." );
			searchString = searchString.replaceAll( "\\*", "\\." );
			searchString = searchString.replaceAll( "\\.", "\\.*" );

			try {
				// grep -E "REGEX-Suchbegriff" table13.xml >> output.txt
				String command = "cmd /c \"\"" + pathToGrepExe + "\" -E" + insensitiveOption + " \""
						+ searchString + "\" \"" + fSearchtable.getAbsolutePath() + "\" >> \""
						+ tempOutFile.getAbsolutePath() + "\"\"";
				/* Das redirect Zeichen verunmöglicht eine direkte eingabe. mit dem geschachtellten Befehl
				 * gehts: cmd /c\"urspruenlicher Befehl\" */

				// System.out.println( command );

				Process proc = null;
				Runtime rt = null;

				getMessageService().logError(
						getTextResourceService().getText( MESSAGE_XML_ELEMENT_OPEN, name ) );
				if ( time ) {
					nowTime = new java.util.Date();
					stringNowTime = sdfStartS.format( nowTime );
					System.out.println( stringNowTime + " Ende der Initialisierung" );
				}

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
					if ( time ) {
						nowTime = new java.util.Date();
						stringNowTime = sdfStartS.format( nowTime );
						System.out.println( stringNowTime + " Ende der Ausführung von GREP" );
					}

				} catch ( Exception e ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B )
									+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
					isValid = false;
				} finally {
					if ( proc != null ) {
						closeQuietly( proc.getOutputStream() );
						closeQuietly( proc.getInputStream() );
						closeQuietly( proc.getErrorStream() );
					}
				}

				if ( time ) {
					nowTime = new java.util.Date();
					stringNowTime = sdfStartS.format( nowTime );
					System.out.println( stringNowTime + " Start der Bereinigung" );
				}

				Scanner scanner = new Scanner( tempOutFile, "UTF-8" );
				contentAll = "";
				content = "";
				contentAll = scanner.useDelimiter( "\\Z" ).next();
				scanner.close();
				content = contentAll;
				/* im contentAll ist jetzt der Gesamtstring, dieser soll anschliessend nur noch aus den 12
				 * ResultateZellen bestehen -> content */
				String nr0 = getConfigurationService().getMaintablePrimarykeyCell();
				String nr1 = getConfigurationService().getcellNumber1();
				String nr2 = getConfigurationService().getcellNumber2();
				String nr3 = getConfigurationService().getcellNumber3();
				String nr4 = getConfigurationService().getcellNumber4();
				String nr5 = getConfigurationService().getcellNumber5();
				String nr6 = getConfigurationService().getcellNumber6();
				String nr7 = getConfigurationService().getcellNumber7();
				String nr8 = getConfigurationService().getcellNumber8();
				String nr9 = getConfigurationService().getcellNumber9();
				String nr10 = getConfigurationService().getcellNumber10();
				String nr11 = getConfigurationService().getcellNumber11();
				if ( nr0.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr0 );
					return false;
				}
				if ( nr1.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr1 );
					return false;
				}
				if ( nr2.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr2 );
					return false;
				}
				if ( nr3.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr3 );
					return false;
				}
				if ( nr4.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr4 );
					return false;
				}
				if ( nr5.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr5 );
					return false;
				}
				if ( nr6.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr6 );
					return false;
				}
				if ( nr7.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr7 );
					return false;
				}
				if ( nr8.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr8 );
					return false;
				}
				if ( nr9.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr9 );
					return false;
				}
				if ( nr10.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr10 );
					return false;
				}
				if ( nr11.startsWith( "Configuration-Error:" ) ) {
					getMessageService().logError(
							getTextResourceService().getText( MESSAGE_XML_MODUL_B ) + nr11 );
					return false;
				}

				File xmlExtracted = new File( siardDatei.getAbsolutePath() + File.separator + "header"
						+ File.separator + "metadata.xml" );
				DocumentBuilderFactory dbfConfig = DocumentBuilderFactory.newInstance();
				DocumentBuilder dbConfig = dbfConfig.newDocumentBuilder();
				Document docConfig = dbConfig.parse( new FileInputStream( xmlExtracted ), "UTF8" );
				docConfig.getDocumentElement().normalize();
				dbfConfig.setFeature( "http://xml.org/sax/features/namespaces", false );

				NodeList nlColumn = docConfig.getElementsByTagName( "column" );
				int counterColumn = nlColumn.getLength();
				/* counterColumn ist zwar zu hoch aber betreffend der performance am besten. durch break
				 * wird zudem abgebrochen, sobald alle behalten wurden. */
				String cellLoop = "";
				String modifString = "";
				// Loop von 1, 2, 3 ... bis counterColumn.
				boolean col0 = false, col1 = false, col2 = false, col3 = false, col4 = false, col5 = false;
				boolean col6 = false, col7 = false, col8 = false, col9 = false, col10 = false, col11 = false;
				for ( int i = 1; i < counterColumn; i++ ) {
					cellLoop = "";
					cellLoop = "c" + i;
					if ( cellLoop.equals( nr0 ) || cellLoop.equals( nr1 ) || cellLoop.equals( nr2 )
							|| cellLoop.equals( nr3 ) || cellLoop.equals( nr4 ) || cellLoop.equals( nr5 )
							|| cellLoop.equals( nr6 ) || cellLoop.equals( nr7 ) || cellLoop.equals( nr8 )
							|| cellLoop.equals( nr9 ) || cellLoop.equals( nr10 ) || cellLoop.equals( nr11 ) ) {
						// wird behalten
						modifString = "c" + i + ">";

						if ( cellLoop.equals( nr0 ) ) {
							content = content.replaceAll( modifString, "col0>" );
							col0 = true;
						} else {
							if ( cellLoop.equals( nr1 ) ) {
								content = content.replaceAll( modifString, "col1>" );
								col1 = true;
							} else {
								if ( cellLoop.equals( nr2 ) ) {
									content = content.replaceAll( modifString, "col2>" );
									col2 = true;
								} else {
									if ( cellLoop.equals( nr3 ) ) {
										content = content.replaceAll( modifString, "col3>" );
										col3 = true;
									} else {
										if ( cellLoop.equals( nr4 ) ) {
											content = content.replaceAll( modifString, "col4>" );
											col4 = true;
										} else {
											if ( cellLoop.equals( nr5 ) ) {
												content = content.replaceAll( modifString, "col5>" );
												col5 = true;
											} else {
												if ( cellLoop.equals( nr6 ) ) {
													content = content.replaceAll( modifString, "col6>" );
													col6 = true;
												} else {
													if ( cellLoop.equals( nr7 ) ) {
														content = content.replaceAll( modifString, "col7>" );
														col7 = true;
													} else {
														if ( cellLoop.equals( nr8 ) ) {
															content = content.replaceAll( modifString, "col8>" );
															col8 = true;
														} else {
															if ( cellLoop.equals( nr9 ) ) {
																content = content.replaceAll( modifString, "col9>" );
																col9 = true;
															} else {
																if ( cellLoop.equals( nr10 ) ) {
																	content = content.replaceAll( modifString, "col10>" );
																	col10 = true;
																} else {
																	if ( cellLoop.equals( nr11 ) ) {
																		content = content.replaceAll( modifString, "col11>" );
																		col11 = true;
																	}
																}
															}
														}
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
					if ( col0 && col1 && col2 && col3 && col4 && col5 && col6 && col7 && col8 && col9
							&& col10 && col11 ) {
						int j = i + 1;
						String deletString = "<c" + j + ">" + ".*" + "</row>";
						content = content.replaceAll( deletString, "</row>" );
						break;
					}
				}

				if ( time ) {
					nowTime = new java.util.Date();
					stringNowTime = sdfStartS.format( nowTime );
					System.out.println( stringNowTime + " Ende der Bereinigung" );
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
