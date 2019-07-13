<?php
include 'connection.php';

$id=$_POST['id'];
$latitude=$_POST['latitude'];
$longitude=$_POST['longitude'];
$sql="DELETE FROM user_place WHERE user_id = '$id' AND $latitude = place_latitude AND $longitude = place_longitude";
$result = mysqli_query($connection,$sql) or die (mysqli_error());

if($result){

    echo "";
}
else{

    echo "error";
}

mysqli_close($connection);

?>
