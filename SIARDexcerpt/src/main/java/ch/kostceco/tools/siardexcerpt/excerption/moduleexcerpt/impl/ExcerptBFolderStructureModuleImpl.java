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

import ch.kostceco.tools.siardexcerpt.exception.moduleexcerpt.ExcerptBFolderStructureException;
import ch.kostceco.tools.siardexcerpt.excerption.ValidationModuleImpl;
import ch.kostceco.tools.siardexcerpt.excerption.moduleexcerpt.ExcerptBFolderStructureModule;
import ch.kostceco.tools.siardexcerpt.service.ConfigurationService;

/** Besteht eine korrekte primäre Verzeichnisstruktur: /header/metadata.xml sowie
 * /header/metadata.xsd und /content */
public class ExcerptBFolderStructureModuleImpl extends ValidationModuleImpl implements
		ExcerptBFolderStructureModule
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
	public boolean validate( File siardDatei, File outFile, String excerptString )
			throws ExcerptBFolderStructureException
	{
		boolean isValid = true;
		File content = new File( siardDatei.getAbsolutePath() + File.separator + "content" );
		File header = new File( siardDatei.getAbsolutePath() + File.separator + "header" );
		File xsd = new File( siardDatei.getAbsolutePath() + File.separator + "header" + File.separator
				+ "metadata.xsd" );
		File metadata = new File( siardDatei.getAbsolutePath() + File.separator + "header"
				+ File.separator + "metadata.xml" );

		if ( !content.exists() || !header.exists() || !xsd.exists() || !metadata.exists() ) {
			isValid = false;
		}
		return isValid;
	}
}
