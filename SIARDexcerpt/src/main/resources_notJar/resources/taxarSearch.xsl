<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/"><html>
	<head>
		<style>
			body {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; }
			table {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; }
			.logow {font-family: Verdana, Geneva, sans-serif; background-color: #ffffff; font-weight:bold; font-size: 32pt; color: #ffffff; }
			.logoff {font-family: Verdana, Geneva, sans-serif; background-color: #ffffff; font-weight:bold; font-size: 18pt; color: #000000; }
			.logo {font-family: Verdana, Geneva, sans-serif; background-color: #000000; font-weight:bold; font-size: 32pt; color: #ffffff; }
			.logov {font-family: Verdana, Geneva, sans-serif; background-color: #000000; font-weight:bold; font-size: 32pt; color: #0cc10c; }
			.logol {font-family: Verdana, Geneva, sans-serif; background-color: #000000; font-weight:bold; font-size: 32pt; color: #000000; }
			h1 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 20pt; color: #000000; }
			h2 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 14pt; color: #000000; }
			h3 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 10pt; color: #000000; }
			h4 {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; color: #808080; }
			.footer {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; color: #808080; }
			tr 	{background-color: #d8d8d8}
			tr.caption {background-color: #ffffff}
			tr.captionb {background-color: #ffffff; font-weight:bold}
			tr.captionm {background-color: #f8dfdf}
			tr.captionio {background-color: #afeeaf; font-weight:bold}
			tr.captioniom {background-color: #ccffcc}
			tr.captioninfo {background-color: #b2b2c5}
			td.caption {background-color: #ffffff}
			td.captionb {background-color: #ffffff; font-weight:bold}
			td.captionkey {font-weight:bold}
			td.right {text-align: right}
		</style>
	</head>
	<body>
		<h1>Suchergebnis aus dem Steuerregister: 
		</h1>
		<div>
			<table width="100%">
				<tr class="caption">
					<td>Name P1:</td>
					<td>Vorname P1:</td>
					<td>Geburtsdatum P1:</td>
					<td class="captionb">AHV-Nr P1:</td>
					<td>Ort P1:</td>
					<td>Name P2:</td>
					<td>Vorname P2:</td>
					<td>Geburtsdatum P2:</td>
				</tr>
				<xsl:for-each select="table/taxDeclarationMainForm/row">
					<tr>
						<td><xsl:value-of select="c6" /></td>
						<td><xsl:value-of select="c7" /></td>
						<td><xsl:value-of select="c10" /></td>
						<td  class="captionkey"><xsl:value-of select="c11" /></td>
						<td><xsl:value-of select="c18" /></td>
						<td><xsl:value-of select="c26" /></td>
						<td><xsl:value-of select="c27" /></td>
						<td><xsl:value-of select="c30" /></td>
					</tr>
				</xsl:for-each>
			</table>
		</div>
	<br />
	<hr noshade="noshade" size="1" />
	<br />
	<p class="footer">Dieses Suchergebnis stammt vom <xsl:value-of select="table/Infos/Start" /> aus dem <xsl:value-of select="table/Infos/Archive" />.</p>
	<p class="footer"><xsl:value-of select="table/Infos/Info" /></p>
	<br />
	</body>
</html></xsl:template></xsl:stylesheet>