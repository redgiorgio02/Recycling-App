<?php
	
	 $data = array();

	
	  
	 
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="login-registration";
	 
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 
	 $sql = "SELECT * FROM points WHERE id = 1";
	 
	 $result = mysqli_query($dbh, $sql);
	 while ($row = mysqli_fetch_array($result)) {
		$nested_data = array();
		$nested_data['paper_points'] = $row['paper_points'];
		$nested_data['plastic_points']= $row['plastic_points'];
		$nested_data['glass_points']= $row['glass_points'];
		$nested_data['alum_points']= $row['alum_points'];
		$data[$row['id']] = $nested_data;
	 }
	 
	 header("Content-Type: application/json");
	 echo json_encode($data);
	 mysqli_close($dbh);
?>