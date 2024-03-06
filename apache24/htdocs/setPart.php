<?php
$con=mysqli_connect("localhost","root","1234","jungcar");







header('content-type: text/html; charset=utf-8');



$query = "select subject, market_idx, img, price from part_market,board where part_market.board_idx = board.board_idx
and part_market.market_idx not in (select market_idx from part_trade) order by market_idx desc limit 10";

$query2 = "select ifnull(count(*),0) as cnt from part_market";


	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}



$result2=mysqli_query($con, $query2);
$rowCnt2= mysqli_num_rows($result2);
$arr2= array();

for($i=0;$i<$rowCnt2;$i++){
      $row= mysqli_fetch_array($result2, MYSQLI_ASSOC);
        $arr2[$i]= $row;      
}
  $jsonData=json_encode(array("webnautes"=>$arr,"cols"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>