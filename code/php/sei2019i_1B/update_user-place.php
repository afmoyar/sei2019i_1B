<?php
include 'connection.php';

$id=$_POST['id'];
$comment=$_POST['comment'];
$place_latitude=$_POST['place_latitude'];
$place_longitude=$_POST['place_longitude'];
$rating=$_POST['rating'];
$sql="UPDATE user_place SET rating = '$rating', comment = '$comment' WHERE user_id = '$id' AND place_latitude='$place_latitude' AND place_longitude='$place_longitude'";
$result = mysqli_query($connection,$sql) or die (mysqli_error());



if($result){

    echo "";
}
else{

    echo "error";
}

mysqli_close($connection);

?>
