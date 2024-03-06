<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user = $_POST['user'];
$table = $_POST['table'];





header('content-type: text/html; charset=utf-8');



$query = "select car_market.sell_idx as sell_idx, name,opt_grade_name, year, mileage, country, price, img, label as label_idx, user_idx from opt_grade, car,model,connection,
 car_market left outer join additional on car_market.sell_idx=additional.sell_idx left outer join label on label.label_idx = additional.label_idx, $table
 where connection.opt_grade_idx = opt_grade.opt_grade_idx and car.car_idx=car_market.car_idx and car.connection_idx=connection.connection_idx and connection.model_idx = model.model_idx and user_idx=$user and car_market.sell_idx = $table.sell_idx
 and car_market.sell_idx not in (select sell_idx from trade) group by car_market.sell_idx";

if ($table==="sh_history"){
  $query=$query." order by show_date desc";
}
	
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