<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$name = $_POST['name'];


	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select food_idx, food_name, kcal*100 as cal, carbo*100 as carbo, protein*100 as protein, fat*100 as fat from food where food_name like '%$name%'");
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>