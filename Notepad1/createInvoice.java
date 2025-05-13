{
    "InvoiceCurrencyCode": "{{CURRENCY_CODE}}",
    "TransactionDate": "{{TRX_DATE}}",
	"CrossReference":"{{REFERENCE}}",
    "TransactionType": "CPQ Cloud Adv Inv",
    "TransactionSource": "ADVANCE_INV_CPQ",
    "BillToCustomerNumber": "{{BILL_TO_CUSTOMER}}",
    "BillToSite": "{{BILL_TO_SITE}}",
    "BusinessUnit": "{{BUSINESS_UNIT}}",
    "PaymentTerms": "CA100",
    "AccountingDate": "{{ACCOUNTING_DATE}}",
    "PurchaseOrder":"{{PO}}",
    "SpecialInstructions" :"{{Instructions}}",
    "receivablesInvoiceLines": [
        {
            "LineNumber": 1,
            "Description": "{{Advance_Invoice}}",
            "Quantity": 1,
            "UnitSellingPrice": {{PRICE}},
            "TaxClassificationCode": "{{TAX_CODE}}",
            "SalesOrder": "{{SALES_ORDER}}",
            "UnitOfMeasure": "Each",
            "LineAmountIncludesTax": "Use Tax Rate Code",
            "receivablesInvoiceLineTaxLines": [
                {
                    "TaxRate": {{TAX_RATE}},
                    "TaxRateCode": "{{TAX_CODE}}",
                    "TaxRegimeCode": "REGM_VAT_KSA",
                    "TaxStatusCode": "STATUS_VAT_KSA",
                    "Tax": "TYPE_VAT_KSA",
                    "TaxAmount": {{TAX_AMOUNT}}
                }
            ]
        }
    ]
}
