import axios from 'axios';
import './Comment.css';
import likes from '../images/thumbs-up.png'
import disLike from '../images/thumbs-down.png'

export default function Comment(props) {

    const { comment ,updateComment} = props;

    const handleVote = async vote => {
        try {
            const data = { vote };
            const response = await axios.post('commodities/1/comments/' + comment.id + '/likes/', data);
            let newComment = response.data.content;
            updateComment(newComment);
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <div class="container comment-container">
            <div class="user-comment-text">
                {comment.text}
            </div>
            <div class="d-flex align-items-center">
                <div class="user-comment-date">
                    {comment.createDate}
                </div>
                <div class="user-comment-username">
                    &#x2022;
                </div>
                <div class="user-comment-username">
                    {comment.commentOwner}
                </div>
            </div>
            <div class="d-flex align-items-center">
                <div class="col-sm-8"></div>
                <div class="col-sm-4 d-flex justify-content-center align-items-center">
                    <div class="comment-rate-text">
                        Is this comment helpful?
                    </div>
                    <div class="comment-rate-num">
                        {comment.likeNumber}
                    </div>
                    <img onClick={() => { handleVote(1) }} class="rate-img img-responsive" src={likes}alt="like" />
                    <div class="comment-rate-num">
                        {comment.dislikeNumber}
                    </div>
                    <img onClick={() => { handleVote(-1) }} class="rate-img img-responsive" src={disLike} alt="dislike" />
                </div>
            </div>
        </div>
    )
}