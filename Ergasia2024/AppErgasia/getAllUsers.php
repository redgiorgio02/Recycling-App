<?php

	 $data = array();
	 
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="login-registration";
	 
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 
	 $sql = "SELECT username, total_points FROM user_details";
	 
	 $result = mysqli_query($dbh, $sql);
	 while ($row = mysqli_fetch_array($result)) {
		$data[$row['username']] = $row["total_points"];
	 }
	 
	 header("Content-Type: application/json");
	 echo json_encode($data);
	 mysqli_close($dbh);
?>