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
		String error = "Configuration-Error: Missing pathtoworkdir";
		return error;
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
		String error = "Configuration-Error: Missing pathtooutput";
		return error;
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
		String error = "Configuration-Error: Missing pathtoxsl";
		return error;
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
		String error = "Configuration-Error: Missing pathtoxslsearch";
		return error;
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
		String error = "Configuration-Error: Missing archive";
		return error;
	}

	@Override
	public String getInsensitive()
	{
		/** Gibt an ob die Suche case sensitive (Gross- und Kleinschreibung werden berücksichtigt) oder
		 * insensitive (Gross- und Kleinschreibung werden ignoriert) sein soll. */
		Object prop = getConfig().getProperty( "insensitive" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing archive";
		return error;
	}

	// ------------------------ Extraktion ------------------------
	@Override
	public String getMaintableFolder()
	{
		/** Gibt den Ordner der Haupttabelle zurück. */
		Object prop = getConfig().getProperty( "maintable.mainfolder" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.mainfolder";
		return error;
	}

	@Override
	public String getMaintableName()
	{
		/** Gibt den Namen der Haupttabelle zurück. */
		Object prop = getConfig().getProperty( "maintable.mainname" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.mainname";
		return error;
	}

	@Override
	public String getSearchtableTitle()
	{
		/** Gibt den Titel der Suche zurück. */
		Object prop = getConfig().getProperty( "maintable.title" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.title";
		return error;
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
		String error = "Configuration-Error: Missing maintable.primarykeyname";
		return error;
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
		String error = "Configuration-Error: Missing maintable.primarykeycell";
		return error;
	}

	@Override
	public String getcellName1()
	{
		/** Gibt den Namen der Suchzelle Nr1 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname1" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname1";
		return error;
	}

	@Override
	public String getcellNumber1()
	{
		/** Gibt den Nummer der Suchzelle Nr1 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber1" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber1";
		return error;
	}

	@Override
	public String getcellName2()
	{
		/** Gibt den Namen der Suchzelle Nr2 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname2" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname2";
		return error;
	}

	@Override
	public String getcellNumber2()
	{
		/** Gibt den Nummer der Suchzelle Nr2 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber2" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber2";
		return error;
	}

	@Override
	public String getcellName3()
	{
		/** Gibt den Namen der Suchzelle Nr3 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname3" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname3";
		return error;
	}

	@Override
	public String getcellNumber3()
	{
		/** Gibt den Nummer der Suchzelle Nr3 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber3" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber3";
		return error;
	}

	@Override
	public String getcellName4()
	{
		/** Gibt den Namen der Suchzelle Nr4 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname4" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname4";
		return error;
	}

	@Override
	public String getcellNumber4()
	{
		/** Gibt den Nummer der Suchzelle Nr4 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber4" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber4";
		return error;
	}

	@Override
	public String getcellName5()
	{
		/** Gibt den Namen der Suchzelle Nr5 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname5" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname5";
		return error;
	}

	@Override
	public String getcellNumber5()
	{
		/** Gibt den Nummer der Suchzelle Nr5 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber5" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber5";
		return error;
	}

	@Override
	public String getcellName6()
	{
		/** Gibt den Namen der Suchzelle Nr6 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname6" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname6";
		return error;
	}

	@Override
	public String getcellNumber6()
	{
		/** Gibt den Nummer der Suchzelle Nr6 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber6" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber6";
		return error;
	}

	@Override
	public String getcellName7()
	{
		/** Gibt den Namen der Suchzelle Nr7 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname7" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname7";
		return error;
	}

	@Override
	public String getcellNumber7()
	{
		/** Gibt den Nummer der Suchzelle Nr7 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber7" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber7";
		return error;
	}

	@Override
	public String getcellName8()
	{
		/** Gibt den Namen der Suchzelle Nr8 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname8" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname8";
		return error;
	}

	@Override
	public String getcellNumber8()
	{
		/** Gibt den Nummer der Suchzelle Nr8 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber8" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber8";
		return error;
	}

	@Override
	public String getcellName9()
	{
		/** Gibt den Namen der Suchzelle Nr9 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname9" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname9";
		return error;
	}

	@Override
	public String getcellNumber9()
	{
		/** Gibt den Nummer der Suchzelle Nr9 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber9" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber";
		return error;
	}

	@Override
	public String getcellName10()
	{
		/** Gibt den Namen der Suchzelle Nr10 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname10" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname10";
		return error;
	}

	@Override
	public String getcellNumber10()
	{
		/** Gibt den Nummer der Suchzelle Nr10 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber10" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber10";
		return error;
	}

	@Override
	public String getcellName11()
	{
		/** Gibt den Namen der Suchzelle Nr11 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellname11" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellname11";
		return error;
	}

	@Override
	public String getcellNumber11()
	{
		/** Gibt den Nummer der Suchzelle Nr11 zurück. */
		Object prop = getConfig().getProperty( "maintable.cellnumber11" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing maintable.cellnumber11";
		return error;
	}

	@Override
	public String getSubtableFolder()
	{
		/** Gibt den Ordner der Subtabelle zurück. */
		Object prop = getConfig().getProperty( "subtables.subtable.folder" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing subtables.subtable.folder";
		return error;
	}

	@Override
	public String getSubtableName()
	{
		/** Gibt den Namen der Subtabelle zurück. */
		Object prop = getConfig().getProperty( "subtables.subtable.name" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing subtables.subtable.name";
		return error;
	}

	@Override
	public String getSubtableForeignkeyCell()
	{
		/** Gibt die Zelle des Fremdschlüssel der Subtabelle zurück. */
		Object prop = getConfig().getProperty( "subtables.subtable.foreignkeycell" );
		if ( prop instanceof String ) {
			String value = (String) prop;
			return value;
		}
		String error = "Configuration-Error: Missing subtables.subtable.foreignkeycell";
		return error;
	}
}
