<?php
include 'connection.php';
$query = "select count(*) from country where admin_id != ''";
$result_count = $connection -> query($query);
if($result_count == 0){
    echo "error";
}

$query = "select name from place where country_name in(select name from country where admin_id != '')";
$response = array();
while($row = $connection->database_fetch_assoc($query)){
	$response[] = $row;
}

$result_count -> close();
?>

