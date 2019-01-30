/* == SIARDexcerpt ==============================================================================
 * The SIARDexcerpt application is used for excerpt a record from a SIARD-File. Copyright (C)
 * 2016-2019 Claire Roethlisberger (KOST-CECO)
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ch.kostceco.tools.siardexcerpt.service.impl.ConfigurationServiceImpl;
import ch.kostceco.tools.siardexcerpt.logging.Logger;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;
import ch.kostceco.tools.siardexcerpt.service.TextResourceService;

public class ConfigurationServiceImpl implements ConfigurationService
{

	private static final Logger	LOGGER		= new Logger( ConfigurationServiceImpl.class );
	Map<String, String>					configMap	= null;
	private TextResourceService	textResourceService;

	public TextResourceService getTextResourceService()
	{
		return textResourceService;
	}

	public void setTextResourceService( TextResourceService textResourceService )
	{
		this.textResourceService = textResourceService;
	}

	public Map<String, String> configMap()
	{
		try {
			File directoryOfConfigfile = new File( System.getenv( "USERPROFILE" ) + File.separator
					+ ".siardexcerpt" + File.separator + "configuration" );
			File configFile = new File( directoryOfConfigfile + File.separator + "siardexcerpt.conf.xml" );
			Document doc = null;

			BufferedInputStream bis = new BufferedInputStream( new FileInputStream( configFile ) );
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse( bis );
			doc.normalize();

			Map<String, String> configMap = new HashMap<String, String>();
			// ------------------------ Allgemeines ------------------------

			/** Gibt den Pfad des Arbeitsverzeichnisses zurück. =
			 * USERPROFILE/.siardexcerpt/temp_SIARDexcerpt **/
			String pathtoworkdir = System.getenv( "USERPROFILE" ) + File.separator + ".siardexcerpt"
					+ File.separator + "temp_SIARDexcerpt";
			File dir = new File( pathtoworkdir );
			if ( !dir.exists() ) {
				dir.mkdirs();
			}
			configMap.put( "PathToWorkDir", pathtoworkdir );

			/** Gibt den Pfad des Output-verzeichnisses zurück. = USERPROFILE/.siardexcerpt/Output **/
			String logs = System.getenv( "USERPROFILE" ) + File.separator + ".siardexcerpt"
					+ File.separator + "Output";
			File dir1 = new File( logs );
			if ( !dir1.exists() ) {
				dir1.mkdirs();
			}
			configMap.put( "PathToOutput", logs );

			String mfolder = "(..)";
			String mname = "(..)";
			String mtitle = "(..)";
			String mpkname = "(..)";
			String mpkcell = "(..)";
			String mc1name = "(..)";
			String mc1number = "(..)";
			String mc2name = "(..)";
			String mc2number = "(..)";
			String mc3name = "(..)";
			String mc3number = "(..)";
			String mc4name = "(..)";
			String mc4number = "(..)";
			String mc5name = "(..)";
			String mc5number = "(..)";
			String mc6name = "(..)";
			String mc6number = "(..)";
			String mc7name = "(..)";
			String mc7number = "(..)";
			String mc8name = "(..)";
			String mc8number = "(..)";
			String mc9name = "(..)";
			String mc9number = "(..)";
			String mc10name = "(..)";
			String mc10number = "(..)";
			String mc11name = "(..)";
			String mc11number = "(..)";
			String st1keyname = "(..)";
			String st1name = "(..)";
			String st1folder = "(..)";
			String st1fkcell = "(..)";
			String st2keyname = "(..)";
			String st2name = "(..)";
			String st2folder = "(..)";
			String st2fkcell = "(..)";
			String st3keyname = "(..)";
			String st3name = "(..)";
			String st3folder = "(..)";
			String st3fkcell = "(..)";
			String st4keyname = "(..)";
			String st4name = "(..)";
			String st4folder = "(..)";
			String st4fkcell = "(..)";
			String st5keyname = "(..)";
			String st5name = "(..)";
			String st5folder = "(..)";
			String st5fkcell = "(..)";
			String st6keyname = "(..)";
			String st6name = "(..)";
			String st6folder = "(..)";
			String st6fkcell = "(..)";
			String st7keyname = "(..)";
			String st7name = "(..)";
			String st7folder = "(..)";
			String st7fkcell = "(..)";
			String st8keyname = "(..)";
			String st8name = "(..)";
			String st8folder = "(..)";
			String st8fkcell = "(..)";
			String st9keyname = "(..)";
			String st9name = "(..)";
			String st9folder = "(..)";
			String st9fkcell = "(..)";
			String st10keyname = "(..)";
			String st10name = "(..)";
			String st10folder = "(..)";
			String st10fkcell = "(..)";
			String st11keyname = "(..)";
			String st11name = "(..)";
			String st11folder = "(..)";
			String st11fkcell = "(..)";
			String st12keyname = "(..)";
			String st12name = "(..)";
			String st12folder = "(..)";
			String st12fkcell = "(..)";
			String st13keyname = "(..)";
			String st13name = "(..)";
			String st13folder = "(..)";
			String st13fkcell = "(..)";
			String st14keyname = "(..)";
			String st14name = "(..)";
			String st14folder = "(..)";
			String st14fkcell = "(..)";
			String st15keyname = "(..)";
			String st15name = "(..)";
			String st15folder = "(..)";
			String st15fkcell = "(..)";
			String st16keyname = "(..)";
			String st16name = "(..)";
			String st16folder = "(..)";
			String st16fkcell = "(..)";
			String st17keyname = "(..)";
			String st17name = "(..)";
			String st17folder = "(..)";
			String st17fkcell = "(..)";
			String st18keyname = "(..)";
			String st18name = "(..)";
			String st18folder = "(..)";
			String st18fkcell = "(..)";
			String st19keyname = "(..)";
			String st19name = "(..)";
			String st19folder = "(..)";
			String st19fkcell = "(..)";
			String st20keyname = "(..)";
			String st20name = "(..)";
			String st20folder = "(..)";
			String st20fkcell = "(..)";

			/** Gibt den Pfad des XSL zurück. **/
			String pathToXSL = doc.getElementsByTagName( "pathtoxsl" ).item( 0 ).getTextContent();
			configMap.put( "PathToXSL", pathToXSL );

			/** Gibt den Pfad des XSL search zurück. **/
			String pathToXSLsearch = doc.getElementsByTagName( "pathtoxslsearch" ).item( 0 )
					.getTextContent();
			configMap.put( "PathToXSLsearch", pathToXSLsearch );

			/** Gibt den Namen des Archivs zurück. **/
			String archive = doc.getElementsByTagName( "archive" ).item( 0 ).getTextContent();
			configMap.put( "Archive", archive );

			/** Gibt an ob die Suche case sensitive (Gross- und Kleinschreibung werden berücksichtigt) oder
			 * insensitive (Gross- und Kleinschreibung werden ignoriert) sein soll. */
			String insensitive = doc.getElementsByTagName( "insensitive" ).item( 0 ).getTextContent();
			configMap.put( "Insensitive", insensitive );

			// ------------------------ Extraktion ------------------------

			/** Gibt den Namen des Archivs zurück. **/
			mfolder = doc.getElementsByTagName( "mfolder" ).item( 0 ).getTextContent();
			configMap.put( "MaintableFolder", mfolder );

			/** Gibt den Namen der Haupttabelle zurück. */
			mname = doc.getElementsByTagName( "mname" ).item( 0 ).getTextContent();
			configMap.put( "MaintableName", mname );

			/** Gibt den Titel der Suche zurück. */
			mtitle = doc.getElementsByTagName( "mtitle" ).item( 0 ).getTextContent();
			configMap.put( "SearchtableTitle", mtitle );

			/** Gibt den Namen des Primärschlüssel der Haupttabelle zurück. */
			mpkname = doc.getElementsByTagName( "mpkname" ).item( 0 ).getTextContent();
			configMap.put( "MaintablePrimarykeyName", mpkname );

			/** Gibt die Zelle des Primärschlüssel der Haupttabelle zurück. */
			mpkcell = doc.getElementsByTagName( "mpkcell" ).item( 0 ).getTextContent();
			configMap.put( "MaintablePrimarykeyCell", mpkcell );

			/** Gibt den Namen der Suchzelle Nr1 zurück. */
			mc1name = doc.getElementsByTagName( "mc1name" ).item( 0 ).getTextContent();
			configMap.put( "CellName1", mc1name );
			/** Gibt den Nummer der Suchzelle Nr1 zurück. */
			mc1number = doc.getElementsByTagName( "mc1number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber1", mc1number );

			/** Gibt den Namen der Suchzelle Nr2 zurück. */
			mc2name = doc.getElementsByTagName( "mc2name" ).item( 0 ).getTextContent();
			configMap.put( "CellName2", mc2name );
			/** Gibt den Nummer der Suchzelle Nr2 zurück. */
			mc2number = doc.getElementsByTagName( "mc2number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber2", mc2number );

			/** Gibt den Namen der Suchzelle Nr3 zurück. */
			mc3name = doc.getElementsByTagName( "mc3name" ).item( 0 ).getTextContent();
			configMap.put( "CellName3", mc3name );
			/** Gibt den Nummer der Suchzelle Nr3 zurück. */
			mc3number = doc.getElementsByTagName( "mc3number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber3", mc3number );

			/** Gibt den Namen der Suchzelle Nr4 zurück. */
			mc4name = doc.getElementsByTagName( "mc4name" ).item( 0 ).getTextContent();
			configMap.put( "CellName4", mc4name );
			/** Gibt den Nummer der Suchzelle Nr4 zurück. */
			mc4number = doc.getElementsByTagName( "mc4number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber4", mc4number );

			/** Gibt den Namen der Suchzelle Nr5 zurück. */
			mc5name = doc.getElementsByTagName( "mc5name" ).item( 0 ).getTextContent();
			configMap.put( "CellName5", mc5name );
			/** Gibt den Nummer der Suchzelle Nr5 zurück. */
			mc5number = doc.getElementsByTagName( "mc5number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber5", mc5number );

			/** Gibt den Namen der Suchzelle Nr6 zurück. */
			mc6name = doc.getElementsByTagName( "mc6name" ).item( 0 ).getTextContent();
			configMap.put( "CellName6", mc6name );
			/** Gibt den Nummer der Suchzelle Nr6 zurück. */
			mc6number = doc.getElementsByTagName( "mc6number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber6", mc6number );

			/** Gibt den Namen der Suchzelle Nr7 zurück. */
			mc7name = doc.getElementsByTagName( "mc7name" ).item( 0 ).getTextContent();
			configMap.put( "CellName7", mc7name );
			/** Gibt den Nummer der Suchzelle Nr7 zurück. */
			mc7number = doc.getElementsByTagName( "mc7number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber7", mc7number );

			/** Gibt den Namen der Suchzelle Nr8 zurück. */
			mc8name = doc.getElementsByTagName( "mc8name" ).item( 0 ).getTextContent();
			configMap.put( "CellName8", mc8name );
			/** Gibt den Nummer der Suchzelle Nr8 zurück. */
			mc8number = doc.getElementsByTagName( "mc8number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber8", mc8number );

			/** Gibt den Namen der Suchzelle Nr9 zurück. */
			mc9name = doc.getElementsByTagName( "mc9name" ).item( 0 ).getTextContent();
			configMap.put( "CellName9", mc9name );
			/** Gibt den Nummer der Suchzelle Nr9 zurück. */
			mc9number = doc.getElementsByTagName( "mc9number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber9", mc9number );

			/** Gibt den Namen der Suchzelle Nr10 zurück. */
			mc10name = doc.getElementsByTagName( "mc10name" ).item( 0 ).getTextContent();
			configMap.put( "CellName10", mc10name );
			/** Gibt den Nummer der Suchzelle Nr10 zurück. */
			mc10number = doc.getElementsByTagName( "mc10number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber10", mc10number );

			/** Gibt den Namen der Suchzelle Nr11 zurück. */
			mc11name = doc.getElementsByTagName( "mc11name" ).item( 0 ).getTextContent();
			configMap.put( "CellName11", mc11name );
			/** Gibt den Nummer der Suchzelle Nr11 zurück. */
			mc11number = doc.getElementsByTagName( "mc11number" ).item( 0 ).getTextContent();
			configMap.put( "CellNumber11", mc11number );

			// ------------------------ Subtable st1 ------------------------
			/** Namen des Schlüssel zurück. */
			st1keyname = doc.getElementsByTagName( "st1keyname" ).item( 0 ).getTextContent();
			configMap.put( "st1Keyname", st1keyname );
			/** Gibt den Ordner der Subtabelle zurück. */
			st1folder = doc.getElementsByTagName( "st1folder" ).item( 0 ).getTextContent();
			configMap.put( "st1Folder", st1folder );
			/** Gibt den Namen der Subtabelle zurück. */
			st1name = doc.getElementsByTagName( "st1name" ).item( 0 ).getTextContent();
			configMap.put( "st1Name", st1name );
			/** Gibt die Zelle des Fremdschlüssel der Subtabelle zurück. */
			st1fkcell = doc.getElementsByTagName( "st1fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st1Fkcell", st1fkcell );

			// ------------------------ Subtable st2 ------------------------
			st2keyname = doc.getElementsByTagName( "st2keyname" ).item( 0 ).getTextContent();
			configMap.put( "st2Keyname", st2keyname );
			st2folder = doc.getElementsByTagName( "st2folder" ).item( 0 ).getTextContent();
			configMap.put( "st2Folder", st2folder );
			st2name = doc.getElementsByTagName( "st2name" ).item( 0 ).getTextContent();
			configMap.put( "st2Name", st2name );
			st2fkcell = doc.getElementsByTagName( "st2fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st2Fkcell", st2fkcell );

			// ------------------------ Subtable st3 ------------------------
			st3keyname = doc.getElementsByTagName( "st3keyname" ).item( 0 ).getTextContent();
			configMap.put( "st3Keyname", st3keyname );
			st3folder = doc.getElementsByTagName( "st3folder" ).item( 0 ).getTextContent();
			configMap.put( "st3Folder", st3folder );
			st3name = doc.getElementsByTagName( "st3name" ).item( 0 ).getTextContent();
			configMap.put( "st3Name", st3name );
			st3fkcell = doc.getElementsByTagName( "st3fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st3Fkcell", st3fkcell );

			// ------------------------ Subtable st4 ------------------------
			st4keyname = doc.getElementsByTagName( "st4keyname" ).item( 0 ).getTextContent();
			configMap.put( "st4Keyname", st4keyname );
			st4folder = doc.getElementsByTagName( "st4folder" ).item( 0 ).getTextContent();
			configMap.put( "st4Folder", st4folder );
			st4name = doc.getElementsByTagName( "st4name" ).item( 0 ).getTextContent();
			configMap.put( "st4Name", st4name );
			st4fkcell = doc.getElementsByTagName( "st4fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st4Fkcell", st4fkcell );

			// ------------------------ Subtable st5 ------------------------
			st5keyname = doc.getElementsByTagName( "st5keyname" ).item( 0 ).getTextContent();
			configMap.put( "st5Keyname", st5keyname );
			st5folder = doc.getElementsByTagName( "st5folder" ).item( 0 ).getTextContent();
			configMap.put( "st5Folder", st5folder );
			st5name = doc.getElementsByTagName( "st5name" ).item( 0 ).getTextContent();
			configMap.put( "st5Name", st5name );
			st5fkcell = doc.getElementsByTagName( "st5fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st5Fkcell", st5fkcell );

			// ------------------------ Subtable st6 ------------------------
			st6keyname = doc.getElementsByTagName( "st6keyname" ).item( 0 ).getTextContent();
			configMap.put( "st6Keyname", st6keyname );
			st6folder = doc.getElementsByTagName( "st6folder" ).item( 0 ).getTextContent();
			configMap.put( "st6Folder", st6folder );
			st6name = doc.getElementsByTagName( "st6name" ).item( 0 ).getTextContent();
			configMap.put( "st6Name", st6name );
			st6fkcell = doc.getElementsByTagName( "st6fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st6Fkcell", st6fkcell );

			// ------------------------ Subtable st7 ------------------------
			st7keyname = doc.getElementsByTagName( "st7keyname" ).item( 0 ).getTextContent();
			configMap.put( "st7Keyname", st7keyname );
			st7folder = doc.getElementsByTagName( "st7folder" ).item( 0 ).getTextContent();
			configMap.put( "st7Folder", st7folder );
			st7name = doc.getElementsByTagName( "st7name" ).item( 0 ).getTextContent();
			configMap.put( "st7Name", st7name );
			st7fkcell = doc.getElementsByTagName( "st7fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st7Fkcell", st7fkcell );

			// ------------------------ Subtable st8 ------------------------
			st8keyname = doc.getElementsByTagName( "st8keyname" ).item( 0 ).getTextContent();
			configMap.put( "st8Keyname", st8keyname );
			st8folder = doc.getElementsByTagName( "st8folder" ).item( 0 ).getTextContent();
			configMap.put( "st8Folder", st8folder );
			st8name = doc.getElementsByTagName( "st8name" ).item( 0 ).getTextContent();
			configMap.put( "st8Name", st8name );
			st8fkcell = doc.getElementsByTagName( "st8fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st8Fkcell", st8fkcell );

			// ------------------------ Subtable st9 ------------------------
			st9keyname = doc.getElementsByTagName( "st9keyname" ).item( 0 ).getTextContent();
			configMap.put( "st9Keyname", st9keyname );
			st9folder = doc.getElementsByTagName( "st9folder" ).item( 0 ).getTextContent();
			configMap.put( "st9Folder", st9folder );
			st9name = doc.getElementsByTagName( "st9name" ).item( 0 ).getTextContent();
			configMap.put( "st9Name", st9name );
			st9fkcell = doc.getElementsByTagName( "st9fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st9Fkcell", st9fkcell );

			// ------------------------ Subtable st10 ------------------------
			st10keyname = doc.getElementsByTagName( "st10keyname" ).item( 0 ).getTextContent();
			configMap.put( "st10Keyname", st10keyname );
			st10folder = doc.getElementsByTagName( "st10folder" ).item( 0 ).getTextContent();
			configMap.put( "st10Folder", st10folder );
			st10name = doc.getElementsByTagName( "st10name" ).item( 0 ).getTextContent();
			configMap.put( "st10Name", st10name );
			st10fkcell = doc.getElementsByTagName( "st10fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st10Fkcell", st10fkcell );

			// ------------------------ Subtable st11 ------------------------
			st11keyname = doc.getElementsByTagName( "st11keyname" ).item( 0 ).getTextContent();
			configMap.put( "st11Keyname", st11keyname );
			st11folder = doc.getElementsByTagName( "st11folder" ).item( 0 ).getTextContent();
			configMap.put( "st11Folder", st11folder );
			st11name = doc.getElementsByTagName( "st11name" ).item( 0 ).getTextContent();
			configMap.put( "st11Name", st11name );
			st11fkcell = doc.getElementsByTagName( "st11fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st11Fkcell", st11fkcell );

			// ------------------------ Subtable st12 ------------------------
			st12keyname = doc.getElementsByTagName( "st12keyname" ).item( 0 ).getTextContent();
			configMap.put( "st12Keyname", st12keyname );
			st12folder = doc.getElementsByTagName( "st12folder" ).item( 0 ).getTextContent();
			configMap.put( "st12Folder", st12folder );
			st12name = doc.getElementsByTagName( "st12name" ).item( 0 ).getTextContent();
			configMap.put( "st12Name", st12name );
			st12fkcell = doc.getElementsByTagName( "st12fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st12Fkcell", st12fkcell );

			// ------------------------ Subtable st13 ------------------------
			st13keyname = doc.getElementsByTagName( "st13keyname" ).item( 0 ).getTextContent();
			configMap.put( "st13Keyname", st13keyname );
			st13folder = doc.getElementsByTagName( "st13folder" ).item( 0 ).getTextContent();
			configMap.put( "st13Folder", st13folder );
			st13name = doc.getElementsByTagName( "st13name" ).item( 0 ).getTextContent();
			configMap.put( "st13Name", st13name );
			st13fkcell = doc.getElementsByTagName( "st13fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st13Fkcell", st13fkcell );

			// ------------------------ Subtable st14 ------------------------
			st14keyname = doc.getElementsByTagName( "st14keyname" ).item( 0 ).getTextContent();
			configMap.put( "st14Keyname", st14keyname );
			st14folder = doc.getElementsByTagName( "st14folder" ).item( 0 ).getTextContent();
			configMap.put( "st14Folder", st14folder );
			st14name = doc.getElementsByTagName( "st14name" ).item( 0 ).getTextContent();
			configMap.put( "st14Name", st14name );
			st14fkcell = doc.getElementsByTagName( "st14fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st14Fkcell", st14fkcell );

			// ------------------------ Subtable st15 ------------------------
			st15keyname = doc.getElementsByTagName( "st15keyname" ).item( 0 ).getTextContent();
			configMap.put( "st15Keyname", st15keyname );
			st15folder = doc.getElementsByTagName( "st15folder" ).item( 0 ).getTextContent();
			configMap.put( "st15Folder", st15folder );
			st15name = doc.getElementsByTagName( "st15name" ).item( 0 ).getTextContent();
			configMap.put( "st15Name", st15name );
			st15fkcell = doc.getElementsByTagName( "st15fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st15Fkcell", st15fkcell );

			// ------------------------ Subtable st16 ------------------------
			st16keyname = doc.getElementsByTagName( "st16keyname" ).item( 0 ).getTextContent();
			configMap.put( "st16Keyname", st16keyname );
			st16folder = doc.getElementsByTagName( "st16folder" ).item( 0 ).getTextContent();
			configMap.put( "st16Folder", st16folder );
			st16name = doc.getElementsByTagName( "st16name" ).item( 0 ).getTextContent();
			configMap.put( "st16Name", st16name );
			st16fkcell = doc.getElementsByTagName( "st16fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st16Fkcell", st16fkcell );

			// ------------------------ Subtable st17 ------------------------
			st17keyname = doc.getElementsByTagName( "st17keyname" ).item( 0 ).getTextContent();
			configMap.put( "st17Keyname", st17keyname );
			st17folder = doc.getElementsByTagName( "st17folder" ).item( 0 ).getTextContent();
			configMap.put( "st17Folder", st17folder );
			st17name = doc.getElementsByTagName( "st17name" ).item( 0 ).getTextContent();
			configMap.put( "st17Name", st17name );
			st17fkcell = doc.getElementsByTagName( "st17fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st17Fkcell", st17fkcell );

			// ------------------------ Subtable st18 ------------------------
			st18keyname = doc.getElementsByTagName( "st18keyname" ).item( 0 ).getTextContent();
			configMap.put( "st18Keyname", st18keyname );
			st18folder = doc.getElementsByTagName( "st18folder" ).item( 0 ).getTextContent();
			configMap.put( "st18Folder", st18folder );
			st18name = doc.getElementsByTagName( "st18name" ).item( 0 ).getTextContent();
			configMap.put( "st18Name", st18name );
			st18fkcell = doc.getElementsByTagName( "st18fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st18Fkcell", st18fkcell );

			// ------------------------ Subtable st19 ------------------------
			st19keyname = doc.getElementsByTagName( "st19keyname" ).item( 0 ).getTextContent();
			configMap.put( "st19Keyname", st19keyname );
			st19folder = doc.getElementsByTagName( "st19folder" ).item( 0 ).getTextContent();
			configMap.put( "st19Folder", st19folder );
			st19name = doc.getElementsByTagName( "st19name" ).item( 0 ).getTextContent();
			configMap.put( "st19Name", st19name );
			st19fkcell = doc.getElementsByTagName( "st19fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st19Fkcell", st19fkcell );

			// ------------------------ Subtable st20 ------------------------
			st20keyname = doc.getElementsByTagName( "st20keyname" ).item( 0 ).getTextContent();
			configMap.put( "st20Keyname", st20keyname );
			st20folder = doc.getElementsByTagName( "st20folder" ).item( 0 ).getTextContent();
			configMap.put( "st20Folder", st20folder );
			st20name = doc.getElementsByTagName( "st20name" ).item( 0 ).getTextContent();
			configMap.put( "st20Name", st20name );
			st20fkcell = doc.getElementsByTagName( "st20fkcell" ).item( 0 ).getTextContent();
			configMap.put( "st20Fkcell", st20fkcell );

			// System.out.println("Value is b : " + (map.get("key") == b));
			bis.close();
			return configMap;

		} catch ( FileNotFoundException e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e ) );
			System.exit( 1 );
		} catch ( ParserConfigurationException e ) {
			String error = e.getMessage() + " (ParserConfigurationException)";
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, error ) );
			System.exit( 1 );
		} catch ( SAXException e ) {
			String error = e.getMessage() + " (SAXException)";
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, error ) );
			System.exit( 1 );
		} catch ( IOException e ) {
			String error = e.getMessage() + " (IOException)";
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, error ) );
			System.exit( 1 );
		}
		return configMap;
	}
}
