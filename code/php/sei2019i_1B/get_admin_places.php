<?php
include 'connection.php';
$query = "select * from place where country_name in(select name from country where admin_id != '')";

$result_place = $connection -> query($query);

if(mysqli_num_rows($result_place) == 0){

    $result_place -> close();
    echo json_encode(array());
    exit();
}

$fields = array("longitude", "latitude", "name", "description", "country_name");

while($place_row = $result_place -> fetch_array()){

    $place = array();

    foreach($fields as $field){
    
        $place[$field] = utf8_encode($place_row[$field]);
    }
    
    $response[] = $place;
}

echo json_encode($response);

$result_place -> close();
?>
s
