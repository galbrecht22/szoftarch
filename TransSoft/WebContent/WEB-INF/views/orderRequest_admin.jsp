<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<body>
<form action="orderSubmitted_admin" method="post">
	<table>
	<tr>
		<td><font face="consolas" size="2px">From(lat):</font></td>
		<td><input type="text" name="from_lat"></td>
		<!--<td><input type="submit" name="button_flat" value="RandomFLat"></td>-->
	</tr>
	<tr>
		<td><font face="consolas" size="2px">From(lon):</font></td>
		<td><input type="text" name="from_lon"></td>
		<!--<td><input type="submit" name="button_flon" value="RandomFLon"></td>-->
	</tr>
	<tr>
		<td><font face="consolas" size="2px">To(lat):</font></td>
		<td><input type="text" name="to_lat"></td>
		<!--<td><input type="submit" name="button_tlat" value="RandomTLat"></td>-->
	</tr>
	<tr>
		<td><font face="consolas" size="2px">To(lon):</font></td>
		<td><input type="text" name="to_lon"></td>
		<!--<td><input type="submit" name="button_tlon" value="RandomTLon"></td>-->
	</tr>
	<tr>
		<td><font face="consolas" size="2px">Mass:</font></td>
		<td><input type="text" name="mass"></td>
	</tr>
	<tr>
		<td><font face="consolas" size="2px">Volume:</font></td>
		<td><input type="text" name="volume"></td>
	</tr>
	<tr>
		<td><font face="consolas" size="2px">Date:</font></td>
		<td><input type="text" name="date"></td>
	</tr>
	</table>
		<input type="submit" value="OrderRequest">
</form>
<form action="computeRequest" method="post">
	<table>
		<tr>
			<td><input type="submit" name="action" value="Compute"/></td>
			<td><input type="text" name="computeDate"></td>
		</tr>
	</table>

</form>
<a href="/TransSoft">Logout</a>
</body>
</html>