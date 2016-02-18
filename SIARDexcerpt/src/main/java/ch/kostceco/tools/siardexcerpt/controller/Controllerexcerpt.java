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

package ch.kostceco.tools.siardexcerpt.controller;

import java.io.File;

import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptAZipException;
import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptBFolderStructureException;
import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptCGrepException;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptAZipModule;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptBFolderStructureModule;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptCGrepModule;
import ch.kostceco.tools.siardexcerpt.logging.Logger;
import ch.kostceco.tools.siardexcerpt.logging.MessageConstants;
import ch.kostceco.tools.siardexcerpt.service.TextResourceService;

/** Der Controller ruft die benötigten Module zur Extraktion in der benötigten Reihenfolge auf.
 * 
 * Die Validierungs-Module werden mittels Spring-Dependency-Injection eingebunden. */

public class Controllerexcerpt implements MessageConstants
{

	private static final Logger						LOGGER	= new Logger( Controllerexcerpt.class );

	private ExcerptAZipModule							excerptAZipModule;

	private ExcerptBFolderStructureModule	excerptBFolderStructureModule;

	private ExcerptCGrepModule						excerptCGrepModule;

	private TextResourceService						textResourceService;

	public ExcerptAZipModule getExcerptAZipModule()
	{
		return excerptAZipModule;
	}

	public void setExcerptAZipModule( ExcerptAZipModule excerptAZipModule )
	{
		this.excerptAZipModule = excerptAZipModule;
	}

	public ExcerptBFolderStructureModule getExcerptBFolderStructureModule()
	{
		return excerptBFolderStructureModule;
	}

	public void setExcerptBFolderStructureModule(
			ExcerptBFolderStructureModule excerptBFolderStructureModule )
	{
		this.excerptBFolderStructureModule = excerptBFolderStructureModule;
	}

	public ExcerptCGrepModule getExcerptCGrepModule()
	{
		return excerptCGrepModule;
	}

	public void setExcerptCGrepModule( ExcerptCGrepModule excerptCGrepModule )
	{
		this.excerptCGrepModule = excerptCGrepModule;
	}

	public TextResourceService getTextResourceService()
	{
		return textResourceService;
	}

	public void setTextResourceService( TextResourceService textResourceService )
	{
		this.textResourceService = textResourceService;
	}

	public boolean executeA( File siardDatei, File outFile, String excerptString )
	{
		boolean valid = true;

		// Excerpt Step A (TODO: SIARD-Datei ins Workverzeichnis extrahieren)

/*		try {
			if ( this.getExcerptAZipModule().validate( siardDatei, outFile, excerptString ) ) {
				this.getExcerptAZipModule().getMessageService().print();
			} else {
				this.getExcerptAZipModule().getMessageService().print();
				// Ein negatives Validierungsresultat in diesem Schritt führt zum Abbruch der weiteren
				// Verarbeitung
				return false;
			}
		} catch ( ExcerptAZipException e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			this.getExcerptAZipModule().getMessageService().print();
			return false;
		} catch ( Exception e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_A )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}
*/
		return valid;

	}

	public boolean executeB( File siardDatei, File outFile, String excerptString )
	{
		boolean valid = true;
		// Excerpt Step B (Struktur Check)
		try {
			if ( this.getExcerptBFolderStructureModule().validate( siardDatei, outFile, excerptString ) ) {
				this.getExcerptBFolderStructureModule().getMessageService().print();
			} else {
				this.getExcerptBFolderStructureModule().getMessageService().print();
				// Ein negatives Validierungsresultat in diesem Schritt führt zum Abbruch der weiteren
				// Verarbeitung
				return false;
			}
		} catch ( ExcerptBFolderStructureException e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_B )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			this.getExcerptBFolderStructureModule().getMessageService().print();
			return false;
		} catch ( Exception e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_B )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}

		return valid;

	}

	public boolean executeOther( File siardDatei, File outFile, String excerptString )
	{
		boolean valid = true;
		// Excerpt Step C
		try {
			if ( this.getExcerptCGrepModule().validate( siardDatei, outFile, excerptString ) ) {
				this.getExcerptCGrepModule().getMessageService().print();
			} else {
				this.getExcerptCGrepModule().getMessageService().print();
				valid = false;
			}
		} catch ( ExcerptCGrepException e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_C )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			this.getExcerptCGrepModule().getMessageService().print();
			return false;
		} catch ( Exception e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_C )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}

		/* // Excerpt Step D try { if ( this.getExcerptDMetadataModule().validate( siardDatei, outFile )
		 * ) { this.getExcerptDMetadataModule().getMessageService().print(); } else {
		 * this.getExcerptDMetadataModule().getMessageService().print(); valid = false; } } catch (
		 * ExcerptDMetadataException e ) { LOGGER.logError( getTextResourceService().getText(
		 * MESSAGE_XML_MODUL_Ad_SIP ) + getTextResourceService().getText( ERROR_XML_UNKNOWN,
		 * e.getMessage() ) ); this.getExcerptDMetadataModule().getMessageService().print(); return
		 * false; } catch ( Exception e ) { LOGGER.logError( getTextResourceService().getText(
		 * MESSAGE_XML_MODUL_Ad_SIP ) + getTextResourceService().getText( ERROR_XML_UNKNOWN,
		 * e.getMessage() ) ); return false; }
		 * 
		 * // Excerpt Step E try { if ( this.getExcerptESipTypeModule().validate( siardDatei, outFile )
		 * ) { this.getExcerptESipTypeModule().getMessageService().print(); } else {
		 * this.getExcerptESipTypeModule().getMessageService().print(); valid = false; } } catch (
		 * ExcerptESipTypeException e ) { LOGGER.logError( getTextResourceService().getText(
		 * MESSAGE_XML_MODUL_Ae_SIP ) + getTextResourceService().getText( ERROR_XML_UNKNOWN,
		 * e.getMessage() ) ); this.getExcerptESipTypeModule().getMessageService().print(); valid =
		 * false; } catch ( Exception e ) { LOGGER.logError( getTextResourceService().getText(
		 * MESSAGE_XML_MODUL_Ae_SIP ) + getTextResourceService().getText( ERROR_XML_UNKNOWN,
		 * e.getMessage() ) ); return false; }
		 * 
		 * // Excerpt Step F try { if ( this.getExcerptFPrimaryDataModule().validate( siardDatei,
		 * outFile ) ) { this.getExcerptFPrimaryDataModule().getMessageService().print(); } else {
		 * this.getExcerptFPrimaryDataModule().getMessageService().print(); valid = false; } } catch (
		 * ExcerptFPrimaryDataException e ) { LOGGER.logError( getTextResourceService().getText(
		 * MESSAGE_XML_MODUL_Af_SIP ) + getTextResourceService().getText( ERROR_XML_UNKNOWN,
		 * e.getMessage() ) ); this.getExcerptFPrimaryDataModule().getMessageService().print(); valid =
		 * false; } catch ( Exception e ) { LOGGER.logError( getTextResourceService().getText(
		 * MESSAGE_XML_MODUL_Af_SIP ) + getTextResourceService().getText( ERROR_XML_UNKNOWN,
		 * e.getMessage() ) ); return false; } */
		return valid;
	}
}
