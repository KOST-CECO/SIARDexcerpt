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
	String	ERROR_IOE																			= "error.ioe";
	String	ERROR_PARAMETER_USAGE													= "error.parameter.usage";
	String	ERROR_LOGDIRECTORY_NODIRECTORY								= "error.logdirectory.nodirectory";
	String	ERROR_LOGDIRECTORY_NOTWRITABLE								= "error.logdirectory.notwritable";
	String	ERROR_WORKDIRECTORY_NOTDELETABLE							= "error.workdirectory.notdeletable";
	String	ERROR_WORKDIRECTORY_NOTWRITABLE								= "error.workdirectory.notwritable";
	String	ERROR_WORKDIRECTORY_EXISTS										= "error.workdirectory.exists";
	String	ERROR_DIADIRECTORY_NOTWRITABLE								= "error.diadirectory.notwritable";
	String	ERROR_SIARDFILE_FILENOTEXISTING								= "error.siardfile.filenotexisting";
	String	ERROR_LOGGING_NOFILEAPPENDER									= "error.logging.nofileappender";
	String	ERROR_CANNOTCREATEZIP													= "error.cannotcreatezip";
	String	ERROR_JHOVECONF_MISSING												= "error.jhoveconf.missing";
	String	ERROR_PARAMETER_OPTIONAL_1										= "error.parameter.optional.1";
	String	ERROR_INCORRECTFILEENDING											= "error.incorrectfileending";
	String	ERROR_INCORRECTFILEENDINGS										= "error.incorrectfileendings";
	String	ERROR_NOFILEENDINGS														= "error.nofileendings";
	String	ERROR_WRONG_JRE																= "error.wrong.jdk";
	String	ERROR_SPECIAL_CHARACTER												= "error.special.character";

	// Globale Meldungen
	String	MESSAGE_XML_SUMMARY_3C												= "message.xml.summary.3c";
	String	MESSAGE_XML_VALFILE														= "message.xml.valfile";
	String	MESSAGE_XML_HEADER														= "message.xml.header";
	String	MESSAGE_XML_START															= "message.xml.start";
	String	MESSAGE_XML_ARCHIVE														= "message.xml.archive";
	String	MESSAGE_XML_FORMATON													= "message.xml.formaton";
	String	MESSAGE_XML_INFO															= "message.xml.info";
	String	MESSAGE_TIFFVALIDATION												= "message.tiffvalidation";
	String	MESSAGE_SIARDVALIDATION												= "message.siardvalidation";
	String	MESSAGE_PDFAVALIDATION												= "message.pdfavalidation";
	String	MESSAGE_PDFAVALIDATION_VL											= "message.pdfavalidation.vl";
	String	MESSAGE_JP2VALIDATION													= "message.jp2validation";
	String	MESSAGE_JPEGVALIDATION												= "message.jpegvalidation";
	String	MESSAGE_SIPVALIDATION													= "message.sipvalidation";
	String	MESSAGE_XML_VALERGEBNIS												= "message.xml.valergebnis";
	String	MESSAGE_XML_VALTYPE														= "message.xml.valtype";
	String	MESSAGE_XML_FORMAT1														= "message.xml.format1";
	String	MESSAGE_XML_FORMAT2														= "message.xml.format2";
	String	MESSAGE_XML_LOGEND														= "message.xml.logend";
	String	MESSAGE_XML_SIP1															= "message.xml.sip1";
	String	MESSAGE_XML_SIP2															= "message.xml.sip2";
	String	MESSAGE_XML_VALERGEBNIS_VALID									= "message.xml.valergebnis.valid";
	String	MESSAGE_XML_VALERGEBNIS_INVALID								= "message.xml.valergebnis.invalid";
	String	MESSAGE_XML_VALERGEBNIS_CLOSE									= "message.xml.valergebnis.close";

	String	MESSAGE_XML_MODUL_A														= "message.xml.modul.a";
	String	MESSAGE_XML_MODUL_B														= "message.xml.modul.b";
	String	MESSAGE_XML_MODUL_C														= "message.xml.modul.c";
	String	MESSAGE_XML_MODUL_SEARCH											= "message.xml.modul.search";

	String	MESSAGE_XML_CONFIGURATION_ERROR_1							= "message.xml.configuration.error.1";
	String	MESSAGE_XML_CONFIGURATION_ERROR_2							= "message.xml.configuration.error.2";
	String	MESSAGE_XML_CONFIGURATION_ERROR_3							= "message.xml.configuration.error.3";

	String	MESSAGE_XML_CONFIGURATION_ERROR_NO_SIGNATURE	= "message.xml.configuration.error.no.signature";
	String	ERROR_XML_CANNOT_INITIALIZE_DROID							= "error.xml.cannot.initialize.droid";

	String	ERROR_XML_UNKNOWN															= "error.xml.unknown";

	String	MESSAGE_XML_ELEMENT_OPEN											= "message.xml.element.open";
	String	MESSAGE_XML_ELEMENT_CONTENT										= "message.xml.element.content";
	String	MESSAGE_XML_ELEMENT_CLOSE											= "message.xml.element.close";

	// *************Meldungen*************************************************************************
	// Modul a Meldungen
	String	ERROR_XML_A_CANNOTEXTRACTZIP									= "error.xml.a.cannotextractzip";

	// Modul b Meldungen
	String	ERROR_XML_B_STRUCTURE													= "error.xml.b.structure";

	// Modul c Meldungen
	String	MESSAGE_XML_C_MISSINGFILE											= "message.xml.c.missingfile";
}
