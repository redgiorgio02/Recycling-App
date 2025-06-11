<?php

	

	$username = $_GET["username"];
	$material_points = $_GET["material_points"];
	$total_points = $_GET["total_points"];
	
	$host="localhost";
	$uname="root";
	$pass="";
	$dbname="login-registration";
	
		
	$dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	mysqli_select_db($dbh, $dbname);
			
	$sql = "UPDATE user_details SET material_points = '".$material_points."', total_points = '".$total_points."' WHERE username = '".$username."'";
	
	echo $sql;
	mysqli_query($dbh, $sql);
	mysqli_close($dbh);
?>