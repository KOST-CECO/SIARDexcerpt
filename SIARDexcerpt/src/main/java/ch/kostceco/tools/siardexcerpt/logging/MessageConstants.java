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

package ch.kostceco.tools.siardexcerpt.logging;

/** Interface für den Zugriff auf Resourcen aus dem ResourceBundle.
 * 
 * @author Rc Claire Röthlisberger, KOST-CECO */
public interface MessageConstants
{

	// Initialisierung und Parameter-Ueberpruefung
	String	ERROR_IOE													= "error.ioe";
	String	ERROR_PARAMETER_USAGE							= "error.parameter.usage";
	String	ERROR_LOGDIRECTORY_NODIRECTORY		= "error.logdirectory.nodirectory";
	String	ERROR_LOGDIRECTORY_NOTWRITABLE		= "error.logdirectory.notwritable";
	String	ERROR_WORKDIRECTORY_NOTWRITABLE		= "error.workdirectory.notwritable";
	String	ERROR_WORKDIRECTORY_EXISTS				= "error.workdirectory.exists";
	String	ERROR_SIARDFILE_FILENOTEXISTING		= "error.siardfile.filenotexisting";
	String	ERROR_CONFIGFILE_FILENOTEXISTING	= "error.configfile.filenotexisting";
	String	ERROR_CONFIGFILEHARD_FILEEXISTING	= "error.configfilehard.fileexisting";
	String	ERROR_LOGGING_NOFILEAPPENDER			= "error.logging.nofileappender";
	String	ERROR_WRONG_JRE										= "error.wrong.jdk";
	String	ERROR_NOINIT											= "error.noinit";

	// Globale Meldungen
	String	MESSAGE_XML_HEADER								= "message.xml.header";
	String	MESSAGE_XML_START									= "message.xml.start";
	String	MESSAGE_XML_ARCHIVE								= "message.xml.archive";
	String	MESSAGE_XML_INFO									= "message.xml.info";
	String	MESSAGE_XML_LOGEND								= "message.xml.logend";

	String	MESSAGE_XML_MODUL_A								= "message.xml.modul.a";
	String	MESSAGE_XML_MODUL_B								= "message.xml.modul.b";
	String	MESSAGE_XML_MODUL_C								= "message.xml.modul.c";
	String	MESSAGE_XML_MODUL_D								= "message.xml.modul.d";

	String	MESSAGE_XML_CONFIGURATION_ERROR_1	= "message.xml.configuration.error.1";

	String	ERROR_XML_UNKNOWN									= "error.xml.unknown";

	String	MESSAGE_XML_ELEMENT_OPEN					= "message.xml.element.open";
	String	MESSAGE_XML_ELEMENT_CONTENT				= "message.xml.element.content";
	String	MESSAGE_XML_ELEMENT_CLOSE					= "message.xml.element.close";

	// *************Meldungen*************************************************************************
	// Modul a Meldungen
	String	ERROR_XML_A_CANNOTEXTRACTZIP			= "error.xml.a.cannotextractzip";

	// Modul b Meldungen
	String	ERROR_XML_B_STRUCTURE							= "error.xml.b.structure";
	String	ERROR_XML_B_CANNOTSEARCHRECORD		= "error.xml.b.cannotsearchrecord";

	// Modul c Meldungen
	String	ERROR_XML_C_MISSINGFILE						= "error.xml.c.missingfile";
	String	ERROR_XML_C_CANNOTEXTRACTRECORD		= "error.xml.c.cannotextractrecord";
}
