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

package ch.kostceco.tools.siardexcerpt.service.impl;

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import ch.kostceco.tools.siardexcerpt.SIARDexcerpt;
import ch.kostceco.tools.siardexcerpt.logging.Logger;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.service.TextResourceService;

public class ConfigurationServiceImpl implements ConfigurationService
{

	private static final Logger	LOGGER	= new Logger( ConfigurationServiceImpl.class );
	XMLConfiguration						config	= null;
	private TextResourceService	textResourceService;

	public TextResourceService getTextResourceService()
	{
		return textResourceService;
	}

	public void setTextResourceService( TextResourceService textResourceService )
	{
		this.textResourceService = textResourceService;
	}

	private XMLConfiguration getConfig()
	{
		if ( this.config == null ) {

			try {

				String path = "configuration/SIARDexcerpt.conf.xml";

				URL locationOfJar = SIARDexcerpt.class.getProtectionDomain().getCodeSource().getLocation();
				String locationOfJarPath = locationOfJar.getPath();

				if ( locationOfJarPath.endsWith( ".jar" ) ) {
					File file = new File( locationOfJarPath );
					String fileParent = file.getParent();
					path = fileParent + "/" + path;
				}

				config = new XMLConfiguration( path );

			} catch ( ConfigurationException e ) {
				LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
						+ getTextResourceService().getText( MESSAGE_XML_CONFIGURATION_ERROR_1 ) );
				LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_LOGEND ) );
				System.exit( 1 );
			}
		}
		return config;
	}

	// ------------------------ Allgemeines ------------------------
	@Override
	public String getPathToWorkDir()
	{
		/** Gibt den Pfad des Arbeitsverzeichnisses zurück. Dieses Verzeichnis wird zum Entpacken des
		 * .zip-Files verwendet.
		 * 
		 * @return Pfad des Arbeitsverzeichnisses */
		Object prop = getConfig().getProperty( "pathtoworkdir" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getPathToOutput()
	{
		/** Gibt den Pfad des Outputverzeichnisses zurück.
		 * 
		 * @return Pfad des Outputverzeichnisses */
		Object prop = getConfig().getProperty( "pathtooutput" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getPathToXSL()
	{
		/** Gibt den Pfad des XSL zurück. */
		Object prop = getConfig().getProperty( "pathtoxsl" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getPathToXSLsearch()
	{
		/** Gibt den Pfad des XSL zurück. */
		Object prop = getConfig().getProperty( "pathtoxslsearch" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getArchive()
	{
		/** Gibt den Namen des Archivs zurück. */
		Object prop = getConfig().getProperty( "archive" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	// ------------------------ Suche ------------------------

	@Override
	public String getSearchtableTitle()
	{
		/** Gibt den Titel der Suche zurück. */
		Object prop = getConfig().getProperty( "searchtable.title" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getSearchtableFolder()
	{
		/** Gibt den Ordner der Suchtabelle zurück. */
		Object prop = getConfig().getProperty( "searchtable.folder" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getSearchtableName()
	{
		/** Gibt den Namen der Suchtabelle zurück. */
		Object prop = getConfig().getProperty( "searchtable.name" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNamekey()
	{
		/** Gibt den Namen der Suchzelle Nr0 respektive Schlüssel zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnamekey" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumberkey()
	{
		/** Gibt den Nummer der Suchzelle Nr0 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumberkey" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName1()
	{
		/** Gibt den Namen der Suchzelle Nr1 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname1" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber1()
	{
		/** Gibt den Nummer der Suchzelle Nr1 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber1" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName2()
	{
		/** Gibt den Namen der Suchzelle Nr2 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname2" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber2()
	{
		/** Gibt den Nummer der Suchzelle Nr2 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber2" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName3()
	{
		/** Gibt den Namen der Suchzelle Nr3 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname3" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber3()
	{
		/** Gibt den Nummer der Suchzelle Nr3 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber3" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName4()
	{
		/** Gibt den Namen der Suchzelle Nr4 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname4" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber4()
	{
		/** Gibt den Nummer der Suchzelle Nr4 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber4" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName5()
	{
		/** Gibt den Namen der Suchzelle Nr5 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname5" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber5()
	{
		/** Gibt den Nummer der Suchzelle Nr5 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber5" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName6()
	{
		/** Gibt den Namen der Suchzelle Nr6 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname6" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber6()
	{
		/** Gibt den Nummer der Suchzelle Nr6 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber6" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellName7()
	{
		/** Gibt den Namen der Suchzelle Nr7 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellname7" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getcellNumber7()
	{
		/** Gibt den Nummer der Suchzelle Nr7 zurück. */
		Object prop = getConfig().getProperty( "searchtable.cellnumber7" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	// ------------------------ Extraktion ------------------------
	@Override
	public String getMaintableFolder()
	{
		/** Gibt den Ordner der Haupttabelle zurück. */
		Object prop = getConfig().getProperty( "maintable.folder" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getMaintableName()
	{
		/** Gibt den Namen der Haupttabelle zurück. */
		Object prop = getConfig().getProperty( "maintable.name" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getMaintablePrimarykeyName()
	{
		/** Gibt den Namen des Primärschlüssel der Haupttabelle zurück. */
		Object prop = getConfig().getProperty( "maintable.primarykeyname" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

	@Override
	public String getMaintablePrimarykeyCell()
	{
		/** Gibt die Zelle des Primärschlüssel der Haupttabelle zurück. */
		Object prop = getConfig().getProperty( "maintable.primarykeycell" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		return null;
	}

}
