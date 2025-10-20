CREATE TABLE IF NOT EXISTS payoutstatus (
    referenceid VARCHAR(100) PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    tazapayid VARCHAR(100),
    currencycode VARCHAR(20), 
    fxrate DOUBLE PRECISION,
    baseamount INTEGER,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


ALTER TABLE payoutstatus ADD COLUMN IF NOT EXISTS tazapayid VARCHAR(100);
ALTER TABLE payoutstatus ADD COLUMN IF NOT EXISTS currencycode VARCHAR(20);
ALTER TABLE payoutstatus ADD COLUMN IF NOT EXISTS fxrate DOUBLE PRECISION;
ALTER TABLE payoutstatus ADD COLUMN IF NOT EXISTS baseamount INTEGER;