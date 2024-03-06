<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$name = $_POST['name'];





header('content-type: text/html; charset=utf-8');

    

$query = "select model.year as name, count(model.year) as cnt from car_market, car, model,connection where car_market.car_idx = car.car_idx 
and model.model_idx = connection.model_idx and connection.connection_idx = car.connection_idx and model.year like '%$name%' and car_market.sell_idx not in (select sell_idx from trade) group by model.year";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
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