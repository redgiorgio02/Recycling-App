<?php

	

	$paper = $_GET["paper_points"];
	$plastic = $_GET["plastic_points"];
	$glass = $_GET["glass_points"];
	$aluminum = $_GET["alum_points"];

	
	$host="localhost";
	$uname="root";
	$pass="";
	$dbname="login-registration";
	
		
	$dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	mysqli_select_db($dbh, $dbname);
			
	$sql = "UPDATE points SET paper_points= '".$paper."', plastic_points= '".$plastic."' , glass_points= '".$glass."' , alum_points= '".$aluminum."' WHERE id = 1 ";
	
	echo $sql;
	mysqli_query($dbh, $sql);
	mysqli_close($dbh);
?>