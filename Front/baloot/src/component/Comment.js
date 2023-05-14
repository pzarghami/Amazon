import axios from 'axios';
import './Comment.css';

export default function Comment(props) {

    const { comment } = props;

    const handleVote = async vote => {
        try {
            const data = { vote };
            const response = await axios.post('commodities/1/comments/' + comment.id + '/likes/', data);
            // let newComment = response.data.content;
            // updateComment(newComment);
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
                    {comment.date}
                </div>
                <div class="user-comment-username">
                    &#x2022;
                </div>
                <div class="user-comment-username">
                    {comment.ownerUserid}
                </div>
            </div>
            <div class="d-flex align-items-center">
                <div class="col-sm-8"></div>
                <div class="col-sm-4 d-flex justify-content-center align-items-center">
                    <div class="comment-rate-text">
                        Is this comment helpful?
                    </div>
                    <div class="comment-rate-num">
                        {comment.like}
                    </div>
                    <img onClick={() => { handleVote(1) }} class="rate-img img-responsive" src="../assets/images/thumbs-up.png" alt="like" />
                    <div class="comment-rate-num">
                        {comment.disLike}
                    </div>
                    <img onClick={() => { handleVote(-1) }} class="rate-img img-responsive" src="../assets/images/thumbs-down.png" alt="dislike" />
                </div>
            </div>
        </div>
    )
}