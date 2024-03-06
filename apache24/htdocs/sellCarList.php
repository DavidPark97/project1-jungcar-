<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user = $_POST['user'];






header('content-type: text/html; charset=utf-8');



$query = "select (select count(sell_idx) from sell_comment where sell_comment.sell_idx = car_market.sell_idx group by (sell_idx)) as cnt,
car_market.sell_idx as sell_idx, name,opt_grade_name, year, mileage, country, price, img, label as label_idx, 
car_own.user_idx from car_own,car,model,connection,opt_grade, 
car_market left outer join additional 
on car_market.sell_idx=additional.sell_idx left outer join label on label.label_idx = additional.label_idx left outer join 
sell_comment on sell_comment.sell_idx = car_market.sell_idx
where car.car_idx=car_market.car_idx and car.connection_idx=connection.connection_idx and
 connection.model_idx = model.model_idx and connection.opt_grade_idx = opt_grade.opt_grade_idx
and car.car_idx = car_own.car_idx and car_own.user_idx=$user and car_market.sell_idx not in (select sell_idx from trade) group by car_market.sell_idx";


	
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