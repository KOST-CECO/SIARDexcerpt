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

import ch.kostceco.tools.siardexcerpt.exception.modulesearch.SearchAPrimarykeyException;
import ch.kostceco.tools.siardexcerpt.excerption.modulesearch.SearchAPrimarykeyModule;
import ch.kostceco.tools.siardexcerpt.logging.Logger;
import ch.kostceco.tools.siardexcerpt.logging.MessageConstants;
import ch.kostceco.tools.siardexcerpt.service.TextResourceService;

/** kostval -->
 * 
 * Der Controller ruft die benötigten Module zur Validierung der JPEG-Datei in der benötigten
 * Reihenfolge auf.
 * 
 * Die Validierungs-Module werden mittels Spring-Dependency-Injection eingebunden. */

public class Controllersearch implements MessageConstants
{

	private static final Logger			LOGGER	= new Logger( Controllersearch.class );
	private TextResourceService			textResourceService;

	private SearchAPrimarykeyModule	searchAPrimarykeyModule;

	public SearchAPrimarykeyModule getValidationAvalidationJpegModule()
	{
		return searchAPrimarykeyModule;
	}

	public void setValidationAvalidationJpegModule( SearchAPrimarykeyModule searchAPrimarykeyModule )
	{
		this.searchAPrimarykeyModule = searchAPrimarykeyModule;
	}

	public TextResourceService getTextResourceService()
	{
		return textResourceService;
	}

	public void setTextResourceService( TextResourceService textResourceService )
	{
		this.textResourceService = textResourceService;
	}

	public boolean executeMandatory( File siardDatei, File outFile, String excerptString )
	{
		boolean valid = true;

		// Validation A
		try {
			if ( this.getValidationAvalidationJpegModule().validate( siardDatei, outFile, excerptString ) ) {
				this.getValidationAvalidationJpegModule().getMessageService().print();
			} else {
				this.getValidationAvalidationJpegModule().getMessageService().print();
				return false;
			}
		} catch ( SearchAPrimarykeyException e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_SEARCH )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			this.getValidationAvalidationJpegModule().getMessageService().print();
			return false;
		} catch ( Exception e ) {
			LOGGER.logError( getTextResourceService().getText( MESSAGE_XML_MODUL_SEARCH )
					+ getTextResourceService().getText( ERROR_XML_UNKNOWN, e.getMessage() ) );
			return false;
		}
		return valid;

	}
}
