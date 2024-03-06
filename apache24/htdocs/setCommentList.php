<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$board_idx = $_POST['board_idx'];
$user_idx = $_POST['user_idx'];






header('content-type: text/html; charset=utf-8');


$query = "WITH RECURSIVE CTE AS (
    SELECT comment_idx, id, user.user_idx, content as comment, date, board_idx,(select 0) as recomment_idx, 
    (select count(*) from cmt_reco where c.comment_idx = cmt_reco.comment_idx) as reco 
	,row_number() over (order by (select count(*) from cmt_reco where c.comment_idx = cmt_reco.comment_idx) desc) as ranking 
	,ifnull((select count(*) from cmt_reco where comment_idx = c.comment_idx and user_idx = $user_idx),0) as state,
	if((row_number() over (order by (select count(*) from cmt_Reco where c.comment_idx = cmt_reco.comment_idx) desc)) <=3,
concat('b',(row_number() over (order by (select count(*) from cmt_Reco where c.comment_idx = cmt_reco.comment_idx) desc))),
concat('d',(row_number() over (order by date)))) as ord
 FROM comment c,user
    WHERE board_idx = $board_idx and user.user_idx = c.user_idx    
    UNION ALL
    SELECT uc.comment_idx, user.id,user.user_idx, uc.content, uc.date, null, uc.recomment_idx, null,null,null,cte.ord
    FROM user,recomment uc
    INNER JOIN CTE ON uc.comment_idx = CTE.comment_idx
	where CTE.board_idx = $board_idx and user.user_idx = uc.user_idx
)
SELECT *
FROM CTE
order by ord,date";


	
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