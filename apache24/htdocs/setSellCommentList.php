<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$sell_idx = $_POST['sell_idx'];





header('content-type: text/html; charset=utf-8');


$query = "WITH RECURSIVE CTE AS (
    SELECT sell_comment_idx, id, user.user_idx, content as comment, date,(select 0) as sell_recomment_idx, sell_idx, 
concat('d',(row_number() over (order by date))) as ord
 FROM sell_comment c,user
    WHERE sell_idx = $sell_idx and user.user_idx = c.user_idx    
    UNION ALL
    SELECT uc.sell_comment_idx, user.id,user.user_idx, uc.content, uc.date, uc.sell_recomment_idx, null, cte.ord
    FROM user,sell_recomment uc
    INNER JOIN CTE ON uc.sell_comment_idx = CTE.sell_comment_idx
	where CTE.sell_idx = $sell_idx and user.user_idx = uc.user_idx
)
SELECT *
FROM CTE
order by ord,date;
";


	
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