import { Link } from "react-router-dom";
import '../css/404.css'
import 'bootstrap'


export default function NotFound404() {

    return (
        <div>
            <div id="clouds">
                <div class="cloud x1"></div>
                <div class="cloud x1_5"></div>
                <div class="cloud x2"></div>
                <div class="cloud x3"></div>
                <div class="cloud x4"></div>
                <div class="cloud x5"></div>
            </div>
            <div class='c'>
                <div class='_404'>400</div>
                    <div class='_1'>BAD</div>
                    <div class='_2'>REQUEST</div>
                    <a class='btn' href='#'>BACK TO COMMODITIES</a>
            </div>
        </div>

    )
}