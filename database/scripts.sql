use dbpdv;

INSERT INTO tbllogin (nmEmail,nmUser,nmPassword,dsType,dsStatus) VALUES ('administrador@gmail.com','administrador','1234567','Administrador','Ativo');

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
tbltypepay.nmTypePay as 'Tipo de Pagamento'
FROM ((( tblSaleProduct
INNER JOIN tblSale ON tblSaleProduct.cdSale = tblSale.cdSale)
INNER JOIN tbltypepay ON tblSale.cd_TypePay = tbltypepay.cdTypePay)
INNER JOIN tblProduct ON tblSaleProduct.cdProduct = tblProduct.cdProduct) WHERE tblSale.cdSale = 1 ORDER BY tblSaleProduct.cdSale;

SELECT  tblSale.cdSale as 'Código da Venda',
tblSale.noQuantityTotal as 'Quantidade total de produtos', 
tblSale.noTotalValue as 'Valor total da venda', 
tbltypepay.nmTypePay as 'Tipo de Pagamento'
FROM (( tblSale
INNER JOIN tbltypepay ON tblsale.cd_TypePay = tblSale.cdSale)
INNER JOIN tbltypepay ON tblpayment.cd_TypePay = tbltypepay.cdTypePay);

SELECT tblSale.cdSale,
tblSale.noQuantityTotal,
tblSale.noTotalValue,
tbltypepay.nmTypePay,
tblLogin.nmUser,
tblCashier.cdCashier
FROM (((
tblSale
INNER JOIN tblTypePay ON tblSale.cd_TypePay = tbltypepay.cdTypePay)
INNER JOIN tblLogin ON tblSale.cd_Login = tblLogin.cdLogin)
INNER JOIN tblCashier ON  tblsale.cd_Cashier = tblcashier.cdCashier);



SELECT * FROM dbpdv.tblsale;


describe tblpayment;

select * from tblpayment;

drop table tblpayment;

ALTER TABLE tblcashier
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;