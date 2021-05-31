package com.easyexcel.demo.write;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhen.ling
 */
public abstract class MoneyUtils {

    private static final ThreadLocal<BigDecimal> FLOATING_RATE = new ThreadLocal<BigDecimal>();

    private static ExchangeRateProvider RATE_PROVIDER;

    public static void setExchangeRateProvider(ExchangeRateProvider exchangeRateProvider) {
        MoneyUtils.RATE_PROVIDER = exchangeRateProvider;
    }

    public static String toString(MonetaryAmount amount) {
        if (amount == null) {
            return "";
        }
        return amount.getNumber().numberValue(BigDecimal.class).toPlainString();
    }

    public static BigDecimal getFlatingRate() {
        return FLOATING_RATE.get();
    }

    public static MonetaryAmount exchange(MonetaryAmount base, String cur) {
        return exchange(base, Monetary.getCurrency(cur));
    }

    public static MonetaryAmount exchange(MonetaryAmount base, CurrencyUnit cur) {
        return exchange(base, cur, null);
    }

    public static MonetaryAmount exchange(MonetaryAmount base, String cur, BigDecimal floatingRate) {
        return exchange(base, Monetary.getCurrency(cur), floatingRate);
    }

    public static MonetaryAmount exchange(MonetaryAmount base, CurrencyUnit cur, BigDecimal floatingRate) {
        if (floatingRate == null) {
            return base.with(getCurrencyConversion(cur));
        } else {
            FLOATING_RATE.set(floatingRate);
            try {
                return base.with(getCurrencyConversion(cur));
            } finally {
                FLOATING_RATE.set(null);
            }
        }
    }

    public static CurrencyConversion getCurrencyConversion(String cur) {
        return getCurrencyConversion(Monetary.getCurrency(cur));
    }

    public static CurrencyConversion getCurrencyConversion(CurrencyUnit cur) {
        return RATE_PROVIDER.getCurrencyConversion(cur);
    }

    public static MonetaryAmount zero() {
        return create(new BigDecimal(0));
    }

    public static MonetaryAmount zero(String cur) {
        return zero(Monetary.getCurrency(cur));
    }

    public static MonetaryAmount zero(CurrencyUnit cur) {
        return create(new BigDecimal(0), cur);
    }

    /**
     * 创建币
     *
     * @param number
     *            数值
     * @return
     */
    public static MonetaryAmount create(BigDecimal number) {
        return create(number, SystemUtils.getCurrency());
    }

    public static MonetaryAmount create(BigDecimal number, String cur) {
        return create(number, Monetary.getCurrency(cur));
    }

    public static MonetaryAmount fen2yuan(Long fen) {
        return fen2yuan(fen, SystemUtils.getCurrency());
    }

    public static MonetaryAmount fen2yuan(Long fen, String cur) {
        return fen2yuan(fen, Monetary.getCurrency(cur));
    }

    public static MonetaryAmount fen2yuan(Long fen, CurrencyUnit cur) {
        if (fen == null) {
            return null;
        }

        // 获取币种的默认小数位个数（CNY的话，元的小数位到分是2位）
        int digits = cur.getDefaultFractionDigits();
        BigDecimal multiples = new BigDecimal(10).pow(cur.getDefaultFractionDigits());

        BigDecimal value = new BigDecimal(fen).divide(multiples).setScale(digits);
        return create(value, cur);
    }

    public static Long yuan2fen(MonetaryAmount amount) {
        if (amount == null) {
            return null;
        }

        NumberValue value = amount.getNumber();

        // 获取币种的默认小数位个数（CNY的话，元的小数位到分是2位）
        int digits = amount.getCurrency().getDefaultFractionDigits();
        BigDecimal multiples = new BigDecimal(10).pow(digits);
        return value.numberValue(BigDecimal.class).multiply(multiples).longValue();
    }

    /**
     * 创建金额
     *
     * @param number
     *            数值
     * @param cur
     *            币种
     * @return
     */
    public static MonetaryAmount create(BigDecimal number, CurrencyUnit cur) {
        if (number == null || cur == null) {
            return null;
        }
        return Money.of(number, cur);
    }

    public static BigDecimal getNumber(MonetaryAmount money) {
        if (money == null) {
            return null;
        }
        return money.getNumber().numberValue(BigDecimal.class);
    }

    public static CurrencyUnit getCurrency(MonetaryAmount money) {
        if (money == null) {
            return null;
        }
        return money.getCurrency();
    }

    /**
     * 计算金额的精度问题 金额 保留位数 舍入模式 默认四位 四舍五入的舍入模式
     *
     * @param amount
     * @return
     */
    public static MonetaryAmount monetaryRounding(MonetaryAmount amount) {
        return monetaryRounding(amount, 2);
    }

    public static MonetaryAmount monetaryRounding(MonetaryAmount amount, int scale) {
        return monetaryRounding(amount, scale, RoundingMode.DOWN);
    }

    public static MonetaryAmount monetaryRounding(MonetaryAmount amount, int scale, RoundingMode roundingMode) {
        return amount.getFactory().setCurrency(amount.getCurrency())
            .setNumber(amount.getNumber().numberValue(BigDecimal.class).setScale(scale, roundingMode)).create();
    }

    public static MonetaryAmount rounding(MonetaryAmount amount, RoundingMode roundingMode) {
        CurrencyUnit cur = amount.getCurrency();
        BigDecimal number = amount.getNumber().numberValue(BigDecimal.class).setScale(cur.getDefaultFractionDigits(), roundingMode);
        return Money.of(number, amount.getCurrency());
    }

    public static boolean strictEqual(MonetaryAmount o1, MonetaryAmount o2) {
        if (null == o1 || null == o2) {
            return false;
        }
        if (null == o1.getNumber() || null == o1.getCurrency() || null == o1.getCurrency().getCurrencyCode()
            || null == o2.getCurrency() || null == o2.getCurrency().getCurrencyCode() || null == o2.getNumber()) {
            return false;
        }
        if (!o1.getCurrency().getCurrencyCode().equals(o2.getCurrency().getCurrencyCode())) {
            return false;
        }
        if (o1.getNumber().compareTo(o2.getNumber()) != 0) {
            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        MonetaryAmount amount = Money.of(new BigDecimal("1.23955"), "THB");
        MonetaryAmount amount2 = amount.multiply(new BigDecimal("100"));
        System.out.println(rounding(amount2, RoundingMode.HALF_UP));
    }
}
