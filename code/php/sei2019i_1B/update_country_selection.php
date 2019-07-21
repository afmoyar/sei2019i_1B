<?php

include 'connection.php';

$request = json_decode(file_get_contents('php://input'), true);

$id = $request['id'];
$unselect = $request['unselect'];
$select = $request['select'];

if(!empty($select)){

    $condition = "name = \"$select[0]\"";

    foreach(array_slice($select, 1) as $country){

        $condition .= " OR name = \"$country\"";
    }
    
    $query = "UPDATE country SET admin_id = \"$id\" WHERE $condition";
    
    $result = mysqli_query($connection, $query) or die (mysqli_error());
}

if(!empty($unselect)){

    $condition = "name = \"$unselect[0]\"";

    foreach(array_slice($unselect, 1) as $country){

        $condition .= " OR name = \"$country\"";
    }
    
    $query = "UPDATE country SET admin_id = NULL WHERE $condition";

    $result = mysqli_query($connection, $query) or die (mysqli_error());
}

mysqli_close($connection);

?>
