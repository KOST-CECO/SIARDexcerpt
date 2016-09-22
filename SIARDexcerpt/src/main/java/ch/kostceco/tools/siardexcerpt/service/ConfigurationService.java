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

package ch.kostceco.tools.siardexcerpt.service;

/** Service Interface für die Konfigurationsdatei.
 * 
 * @author Rc Claire Röthlisberger, KOST-CECO */
public interface ConfigurationService extends Service
{
	// ------------------------ Allgemeines ------------------------
	/** Pfad zu XSL-File, damit der extrahierte Record dargestellt werden kann
	 * 
	 * @return Pfad des XSL-File zum extrahierten Record */
	String getPathToXSL();

	/** Pfad zu XSL-File, damit das Suchergebnis dargestellt werden kann
	 * 
	 * @return Pfad des XSL-File zum Suchergebnis */
	String getPathToXSLsearch();

	/** Gibt den Pfad des Arbeitsverzeichnisses zurück. Dieses Verzeichnis wird z.B. zum Entpacken des
	 * .zip-Files verwendet.
	 * 
	 * @return Pfad des Arbeitsverzeichnisses */
	String getPathToWorkDir();

	/** Gibt den Pfad des Outputverzeichnisses zurück.
	 * 
	 * @return Pfad des Outputverzeichnisses */
	String getPathToOutput();

	/** Gibt den Namen des Archivs zurück.
	 * 
	 * @return Namen des Archivs */
	String getArchive();

	/** Gibt an ob die Suche case sensitive (Gross- und Kleinschreibung werden berücksichtigt) oder
	 * insensitive (Gross- und Kleinschreibung werden ignoriert) sein soll.
	 * 
	 * @return grep Option -i */
	String getInsensitive();

	// ------------------------ Extraktion ------------------------
	/** Gibt den Ordner der Haupttabelle zurück.
	 * 
	 * @return Ordner der Haupttabelle */
	String getMaintableFolder();

	/** Gibt den Namen der Haupttabelle zurück.
	 * 
	 * @return Name der Haupttabelle */
	String getMaintableName();

	/** Gibt den Titel der Suche zurück.
	 * 
	 * @return Titel der Suche */
	String getSearchtableTitle();

	/** Gibt den Namen des Primärschlüssel der Haupttabelle zurück.
	 * 
	 * @return Namen des Primärschlüssel der Haupttabelle */
	String getMaintablePrimarykeyName();

	/** Gibt die Zelle des Primärschlüssel der Haupttabelle zurück.
	 * 
	 * @return Zelle des Primärschlüssel der Haupttabelle */
	String getMaintablePrimarykeyCell();

	/** Gibt den Namen der Suchzelle Nr1 zurück.
	 * 
	 * @return Name der Suchzelle Nr1 */
	String getcellName1();

	/** Gibt den Nummer der Suchzelle Nr1 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr1 */
	String getcellNumber1();

	/** Gibt den Namen der Suchzelle Nr2 zurück.
	 * 
	 * @return Name der Suchzelle Nr2 */
	String getcellName2();

	/** Gibt den Nummer der Suchzelle Nr2 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr2 */
	String getcellNumber2();

	/** Gibt den Namen der Suchzelle Nr3 zurück.
	 * 
	 * @return Name der Suchzelle Nr3 */
	String getcellName3();

	/** Gibt den Nummer der Suchzelle Nr3 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr3 */
	String getcellNumber3();

	/** Gibt den Namen der Suchzelle Nr4 zurück.
	 * 
	 * @return Name der Suchzelle Nr4 */
	String getcellName4();

	/** Gibt den Nummer der Suchzelle Nr4 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr4 */
	String getcellNumber4();

	/** Gibt den Namen der Suchzelle Nr5 zurück.
	 * 
	 * @return Name der Suchzelle Nr5 */
	String getcellName5();

	/** Gibt den Nummer der Suchzelle Nr5 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr5 */
	String getcellNumber5();

	/** Gibt den Namen der Suchzelle Nr6 zurück.
	 * 
	 * @return Name der Suchzelle Nr6 */
	String getcellName6();

	/** Gibt den Nummer der Suchzelle Nr6 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr6 */
	String getcellNumber6();

	/** Gibt den Namen der Suchzelle Nr7 zurück.
	 * 
	 * @return Name der Suchzelle Nr7 */
	String getcellName7();

	/** Gibt den Nummer der Suchzelle Nr7 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr7 */
	String getcellNumber7();

	/** Gibt den Namen der Suchzelle Nr8 zurück.
	 * 
	 * @return Name der Suchzelle Nr8 */
	String getcellName8();

	/** Gibt den Nummer der Suchzelle Nr8 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr8 */
	String getcellNumber8();

	/** Gibt den Namen der Suchzelle Nr9 zurück.
	 * 
	 * @return Name der Suchzelle Nr9 */
	String getcellName9();

	/** Gibt den Nummer der Suchzelle Nr9 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr9 */
	String getcellNumber9();

	/** Gibt den Namen der Suchzelle Nr10 zurück.
	 * 
	 * @return Name der Suchzelle Nr10 */
	String getcellName10();

	/** Gibt den Nummer der Suchzelle Nr10 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr10 */
	String getcellNumber10();

	/** Gibt den Namen der Suchzelle Nr11 zurück.
	 * 
	 * @return Name der Suchzelle Nr11 */
	String getcellName11();

	/** Gibt den Nummer der Suchzelle Nr11 zurück.
	 * 
	 * @return Nummer der Suchzelle Nr11 */
	String getcellNumber11();

	/** Gibt den Ordner der Subtabelle zurück.
	 * 
	 * @return Ordner der Subtabelle */
	String getSubtableFolder();

	/** Gibt den Namen der Subtabelle zurück.
	 * 
	 * @return Name der Subtabelle */
	String getSubtableName();

	/** Gibt die Zelle des Fremdschlüssel der Subtabelle zurück.
	 * 
	 * @return Zelle des Fremdschlüssel der Subtabelle */
	String getSubtableForeignkeyCell();

}
