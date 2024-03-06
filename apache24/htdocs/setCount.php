  <?php
$con=mysqli_connect("localhost","root","1234","jungcar");
header('content-type: text/html; charset=utf-8');


$brand =  $_POST['brand'];
$model = $_POST['model'];
$grade = $_POST['grade'];
$minmilesage = $_POST['minmilesage'];
$maxmilesage = $_POST['maxmilesage'];
$minprice = $_POST['minprice'];  
$maxprice = $_POST['maxprice'];
$country = $_POST['country'];
$own = $_POST['own'];



$query = "select count(*) as cnt from car_market, car, model, connection, fuel 
where car_market.car_idx = car.car_idx and model.model_idx = connection.model_idx 
and connection.connection_idx = car.connection_idx and car.fuel_idx = fuel.fuel_idx and car_market.sell_idx not in (select sell_idx from trade)";

$cond = " ";
if(!empty($brand)){
  $cond = $cond." and brand='$brand'";
}

if(!empty($model)){
  $cond = $cond." and year='$model'";
}

if(!empty($grade)){ 

  $cond = $cond." and fuel_name='$grade'";
}

if(!empty($country)){
  $cond = $cond." and country='$country'";
}

if(!empty($minmilesage)){

  $cond = $cond." and mileage between $minmilesage and $maxmilesage";
}

if(!empty($minprice)){

  $cond = $cond." and price between $minprice and $maxprice";
}

if(strcmp($own,"y")==0){
  $cond = $cond." and own='y'"; 
}else if(strcmp($own,"n")==0){
  $cond = $cond." and own='n'"; 
}

$query = $query.$cond;


	
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