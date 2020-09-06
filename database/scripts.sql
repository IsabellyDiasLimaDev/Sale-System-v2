use dbpdv;

describe tblcashierlogin;
describe tbllogin;

SELECT nmUser ,cdCashier, opening, closing, created_at, updated_at
FROM tblLogin, tblcashieruser
WHERE tblLogin.cdLogin = tblcashieruser.cdLogin;

ALTER TABLE tblcashieruser
ADD COLUMN cdCashierUser INTEGER;

ALTER TABLE tblcashieruser
ADD CONSTRAINT PK_CashierUser PRIMARY KEY (cdCashierUser);

drop table tblcashierlogin;

select * from tblcashier;

UPDATE tblcashier
SET opening = 1, closing=1
WHERE cdCashier = 1;

describe tblsaleproduct;
describe tblproduct;

select * from tblsaleproduct;

SELECT MAX(cdSale) as 'cdSale' from tblSale;
INSERT INTO tblsaleproduct (cdSale,cdProduct,noValue,noQuantity) VALUES (8,3,8.00,2);

select * from tblproduct;

SELECT  tblSale.cdSale as 'Código da Venda',
tblSale.noQuantityTotal as 'Quantidade total de produtos', 
tblSale.noTotalValue as 'Valor total da venda', 
tblProduct.nmDescription as 'Descrição do Produto',
tblSaleProduct.noValue as 'Valor total do produto',
tblSaleProduct.noQuantity as 
'Quantidade Total do produto', 
tblProduct.cdProduct as 'Código do produto',
tbltypepay.nmTypePay as 'Tipo de Pagamento',
tblPayment.noParcialValue as 'Valor Parcial'
FROM (((( tblSaleProduct
INNER JOIN tblSale ON tblSaleProduct.cdSale = tblSale.cdSale)
INNER JOIN tblPayment ON tblSaleProduct.cdSale = tblPayment.cd_Sale)
INNER JOIN tbltypepay ON tblPayment.cd_TypePay = tbltypepay.cdTypePay)
INNER JOIN tblProduct ON tblSaleProduct.cdProduct = tblProduct.cdProduct) ORDER BY tblSaleProduct.cdSale;

describe tblpayment;

select * from tblpayment;

drop table tblpayment;

ALTER TABLE tblcashier
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;