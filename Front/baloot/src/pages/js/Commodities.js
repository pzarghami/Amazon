
import logo from '../../images/toggle.png'
import '../css/commodities.css'
import '../css/root.css'
import '../css/footer.css'


export default function Commodities() {
    return (
        <>
            <div class="page-container">
                <div class="sort-container">
                    <span class="available-sort-container">Available commodities</span>
                    <img class="toggle-sort-container" src={logo} alt="toggle" />
                    <span class="sort-by-sort-container">sort by:</span>
                    <span class="sorted-by-sort-container">name</span>
                    <div class="category-sort-container">
                        <span class="align-middle">price</span>
                    </div>
                </div>
                <div class="products-container">
                    <div class="row">
                        <div class=" ml-0 product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                        <div class="product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                        <div class="product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                        <div class=" mr-0 product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <div class="mr-0 product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                        <div class="product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                        <div class="product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                        <div class="ml-0 product">
                            <span class="title">Huawei nova 9</span>
                            <span class="stock">1 left in stock</span>
                            <img src="../../images/product.png" />
                            <div class="actions">
                                <span class="price">
                                    300$
                                </span>
                                <div class="add-to-cart">
                                    <span>add to cart</span>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

