<?php

include 'connection.php';
$name=$_POST['name'];
$password=$_POST['password'];

$sql="INSERT INTO user (name, password) VALUES('$name','$password')";
mysqli_query($connection,$sql) or die (mysqli_error());
mysqli_close($connection);

?>