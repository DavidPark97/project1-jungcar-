<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$brand = $_POST['brand'];
$model = $_POST['model'];
$grade = $_POST['grade'];
$minmilesage = $_POST['minmilesage'];
$maxmilesage = $_POST['maxmilesage'];
$minprice = $_POST['minprice'];
$maxprice = $_POST['maxprice'];
$country = $_POST['country'];
$own = $_POST['own'];
$name = $_POST['name'];
$usr = $_POST['user'];



if(empty($usr)){
  $usr = "0";
}


header('content-type: text/html; charset=utf-8');



$query = "select car_market.sell_idx as sell_idx, model.name,opt_grade_name, year, mileage, country, price, img, label as label_idx, user_idx 
from opt_grade,car,model,connection,fuel, car_market left outer join additional on car_market.sell_idx=additional.sell_idx left outer join label on label.label_idx = additional.label_idx 
left outer join bookmark on car_market.sell_idx = bookmark.sell_idx and user_idx=$usr
where car.car_idx=car_market.car_idx and car.connection_idx=connection.connection_idx and opt_grade.opt_grade_idx = connection.opt_grade_idx
and connection.model_idx = model.model_idx and fuel.fuel_idx = car.fuel_idx and car_market.sell_idx not in (select sell_idx from trade)" ;
$cond = " ";
if(!empty($brand)){
  $cond = $cond." and brand='$brand'";
}

if(!empty($name)){
    $cond = $cond." and year like '%$name%'";
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
$query = $query." group by car_market.sell_idx order by car_market.date desc, car_market.sell_idx desc";


	
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