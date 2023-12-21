import { useEffect, useState, MouseEvent } from 'react';
import { Link } from 'react-router-dom';
import './style.css';

interface PostJson {
  "postId":number;
  "imageUrl":string;
  "title":string;
  "price":number;
}
// component: 메인 페이지 컴포넌트 //
function Main() {
  const [postJson, setPostJson] = useState<PostJson[]>();

  useEffect( () => {
    const POST_LIST_ENDPOINT = `/deal-service/api/v1/main?page=0`; // 페이지 파라미터를 직접 URL에 추가

    fetch(POST_LIST_ENDPOINT, {
      method: "GET"
    })
      .then(res => res.json()) // res.json()을 호출하여 JSON 데이터를 가져옴
      .then(data => {
        console.log(data); // 가져온 데이터를 콘솔에 출력하거나 원하는 작업 수행
        setPostJson(data['content']);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);

  // // event-handler: 상세항목 click event handler //
  // const onDetailClickHandler = (event: MouseEvent<HTMLInputElement>) => {
  //   // href={`/board/detail/${post.postId}`}
  //   console.log(event.target);
  // }

  return (
    <>
      <div className = "postList">
        {postJson?.map(post => (
          //div, event-handler
          <Link key={post.postId} to={`/board/detail/${post.postId}`}>
            <img src={post.imageUrl} />
            <p>{post.title}</p>
            <p>{post.price}</p>
          </Link>
        ))}
      </div>
    </>
    );
}
export default Main;

