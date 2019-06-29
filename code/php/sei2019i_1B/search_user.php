<?php

include 'connection.php';
$id =$_GET['id'];

$sql = "SELECT * FROM user WHERE id = '$id'";
$count = $connection -> query($sql);

while($row=$result -> fetch_array()){
	 $user[] = array_map('utf8_encode', $row);
	}

echo json_encode($user);
$result -> close();

?>