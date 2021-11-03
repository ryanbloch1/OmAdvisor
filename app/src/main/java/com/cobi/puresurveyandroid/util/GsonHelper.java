package com.cobi.puresurveyandroid.util;

import com.cobi.puresurveyandroid.model.Pipeline;
import com.cobi.puresurveyandroid.model.PipelineProduct;
import com.cobi.puresurveyandroid.model.PipelineProductResponse;
import com.cobi.puresurveyandroid.model.PipelineResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class GsonHelper {
    private static final String ISO8601_WEBSERVICE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private static final Logger logger = Logger.getLogger(GsonHelper.class.getName());
    private static final TypeAdapter<Integer> mTolerantIntegerAdapter = new TypeAdapter<Integer>() {
        @Override
        public void write(JsonWriter out, Integer value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Integer read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected Integer, got boolean with value:" + bool);
                    return bool ? 1 : 0;
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    double value = in.nextDouble();
                    if (value % 1 != 0) {
                        logger.log(Level.SEVERE, "Expected Integer, got double with value:" + value);
                    }
                    return (int) Math.round(value);
                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected Integer, got string with value:" + string);

                    try {
                        return parseInt(string);
                    } catch (Exception e) {
                        return null;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected Integer, got some unknown type with peek value:" + peek.toString());
                    return null;
            }
        }
    };
    private static final TypeAdapter<Integer> mTolerantIntAdapter = new TypeAdapter<Integer>() {
        @Override
        public void write(JsonWriter out, Integer value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Integer read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected Integer, got boolean with value:" + bool);
                    return bool ? 1 : 0;
                case NULL:
                    in.nextNull();
                    return 0;
                case NUMBER:
                    double value = in.nextDouble();
                    if (value % 1 != 0) {
                        logger.log(Level.SEVERE, "Expected Integer, got double with value:" + value);
                    }
                    return (int) Math.round(value);
                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected Integer, got string with value:" + string);

                    try {
                        return parseInt(string);
                    } catch (Exception e) {
                        return 0;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected Integer, got some unknown type with peek value:" + peek.toString());
                    return 0;
            }
        }
    };
    private static final TypeAdapter<Double> mTolerantDoubleAdapter = new TypeAdapter<Double>() {
        @Override
        public void write(JsonWriter out, Double value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Double read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected Double, got boolean with value:" + bool);
                    return bool ? 1D : 0D;
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextDouble();

                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected Double, got string with value:" + string);

                    try {
                        return parseDouble(string);
                    } catch (Exception e) {
                        return null;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected Double, got some unknown type with peek value:" + peek.toString());
                    return null;
            }
        }
    };
    private static final TypeAdapter<Double> mTolerantdoubleAdapter = new TypeAdapter<Double>() {
        @Override
        public void write(JsonWriter out, Double value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Double read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected Double, got boolean with value:" + bool);
                    return bool ? 1D : 0D;
                case NULL:
                    in.nextNull();
                    return 0D;
                case NUMBER:
                    return in.nextDouble();

                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected Double, got string with value:" + string);
                    try {
                        return parseDouble(string);
                    } catch (Exception e) {
                        return 0D;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected Double, got some unknown type with peek value:" + peek.toString());
                    return 0D;
            }
        }
    };
    private static final TypeAdapter<String> mTolerantStringAdapter = new TypeAdapter<String>() {
        @Override
        public void write(JsonWriter out, String value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public String read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected String, got boolean with value:" + bool);
                    return bool ? "true" : "false";
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    double value = in.nextDouble();
                    logger.log(Level.SEVERE, "Expected String, got number with value:" + value);
                    return String.valueOf(value);
                case STRING:
                    return in.nextString();
                default:
                    logger.log(Level.SEVERE, "Expected String, got some unknown type with peek value:" + peek.toString());
                    return null;
            }
        }
    };
    private static final TypeAdapter<Long> mTolerantLongAdapter = new TypeAdapter<Long>() {
        @Override
        public void write(JsonWriter out, Long value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Long read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected Long, got boolean with value:" + bool);
                    return bool ? 1L : 0L;
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return (long) in.nextDouble();
                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected Long, got String with value:" + string);
                    try {
                        return Long.parseLong(string);
                    } catch (Exception e) {
                        return null;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected Long, got some unknown type with peek value:" + peek.toString());
                    return null;
            }
        }
    };
    private static final TypeAdapter<Long> mTolerantlongAdapter = new TypeAdapter<Long>() {
        @Override
        public void write(JsonWriter out, Long value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Long read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    return bool ? 1L : 0L;
                case NULL:
                    in.nextNull();
                    return 0L;
                case NUMBER:
                    return (long) in.nextDouble();
                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected long, got String with value:" + string);
                    try {
                        return Long.parseLong(string);
                    } catch (Exception e) {
                        return 0L;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected long, got some unknown type with peek value:" + peek.toString());
                    return 0L;
            }
        }
    };
    private static final TypeAdapter<Boolean> mTolerantbooleanAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return false;
                case NUMBER:
                    double value = in.nextDouble();
                    if (value < 0 || value > 1) {
                        logger.log(Level.SEVERE, "Expected Boolean, got number with value:" + value);
                        return null;
                    } else {
                        return value != 0;
                    }
                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected boolean, got String with value:" + string);
                    try {
                        return Boolean.parseBoolean(string);
                    } catch (Exception e) {
                        return false;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected boolean, got some unknown type with peek value:" + peek.toString());
                    return false;
            }
        }
    };
    private static final TypeAdapter<Boolean> mTolerantBooleanAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    double value = in.nextDouble();
                    if (value < 0 || value > 1) {
                        logger.log(Level.SEVERE, "Expected boolean, got number with value:" + value);
                        return null;
                    } else {
                        return value != 0;
                    }
                case STRING:
                    String string = in.nextString();
                    logger.log(Level.SEVERE, "Expected boolean, got String with value:" + string);
                    try {
                        return Boolean.parseBoolean(string);
                    } catch (Exception e) {
                        return null;
                    }
                default:
                    logger.log(Level.SEVERE, "Expected boolean, got some unknown type with peek value:" + peek.toString());
                    return null;
            }
        }
    };
    private static final TypeAdapter<Date> mTolerantDateAdapter = new TypeAdapter<Date>() {

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                // Create an instance of SimpleDateFormat used for formatting
                // the string representation of date (month/day/year)
                DateFormat df = new SimpleDateFormat(ISO8601_WEBSERVICE_FORMAT, Locale.getDefault());

                String dateFormatAsString = df.format(value);
                out.value(dateFormatAsString);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    boolean bool = in.nextBoolean();
                    logger.log(Level.SEVERE, "Expected Date String, got Boolean with value:" + bool);
                    return null;
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    double value = in.nextDouble();
                    logger.log(Level.SEVERE, "Expected boolean, got number with value:" + value);
                    return null;
                case STRING:
                    String string = in.nextString();
                    try {
                        DateFormat df = new SimpleDateFormat(ISO8601_WEBSERVICE_FORMAT, Locale.getDefault());
                        //                        if (dateType == Date.class) {
                        return df.parse(string);
                        //                        } else if (dateType == Timestamp.class) {
                        //                            return new Timestamp(date.getTime());
                        //                        } else if (dateType == java.sql.Date.class) {
                        //                            return new java.sql.Date(date.getTime());
                        //                        } else {
                        //This must never happen: dateType is guarded in the primary constructor
                        //                            throw new AssertionError();
                        //                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new JsonSyntaxException(string, e);
                    }
                default:
                    logger.log(Level.SEVERE, "Expected boolean, got some unknown type with peek value:" + peek.toString());
                    return null;
            }
        }
    };



    private static final TypeAdapter<PipelineResponse> mTolerantPipelineAdapter = new TypeAdapter<PipelineResponse>() {

        @Override
        public void write(JsonWriter out, PipelineResponse value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                Gson gson = new Gson();
                out.value( gson.toJson(value));
            }
        }

        @Override
        public PipelineResponse read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();

            PipelineResponse pipelineResponse = new PipelineResponse();

            Gson gson = new Gson();

            switch (peek) {
                case BEGIN_ARRAY:

                    List<Pipeline> list = gson.fromJson(in,new TypeToken<List<Pipeline>>(){}.getType());

                    pipelineResponse.setPipelines(list);

                    return pipelineResponse;
                case BEGIN_OBJECT:

                    return gson.fromJson(in,PipelineResponse.class);
                default:
                    logger.log(Level.SEVERE, "response type neither an object or an array" + peek.toString());
                    return null;
            }
        }
    };




    private static final TypeAdapter<PipelineProductResponse> mTolerantPipelineProductAdapter = new TypeAdapter<PipelineProductResponse>() {

        @Override
        public void write(JsonWriter out, PipelineProductResponse value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                Gson gson = new Gson();
                out.value( gson.toJson(value));
            }
        }

        @Override
        public PipelineProductResponse read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();

            PipelineProductResponse pipelineProductResponse = new PipelineProductResponse();

            Gson gson = new Gson();

            switch (peek) {
                case BEGIN_ARRAY:

                    List<PipelineProduct> list = gson.fromJson(in,new TypeToken<List<PipelineProduct>>(){}.getType());

                    pipelineProductResponse.setPipelineProducts(list);

                    return pipelineProductResponse;
                case BEGIN_OBJECT:

                    return gson.fromJson(in,PipelineResponse.class);
                default:
                    logger.log(Level.SEVERE, "response type neither an object or an array" + peek.toString());
                    return null;
            }
        }
    };

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setDateFormat(ISO8601_WEBSERVICE_FORMAT);

        builder.registerTypeAdapter(Boolean.class, mTolerantBooleanAdapter);
        builder.registerTypeAdapter(boolean.class, mTolerantbooleanAdapter);
        builder.registerTypeAdapter(Integer.class, mTolerantIntegerAdapter);
        builder.registerTypeAdapter(int.class, mTolerantIntAdapter);
        builder.registerTypeAdapter(Long.class, mTolerantLongAdapter);
        builder.registerTypeAdapter(long.class, mTolerantlongAdapter);
        builder.registerTypeAdapter(Double.class, mTolerantDoubleAdapter);
        builder.registerTypeAdapter(double.class, mTolerantdoubleAdapter);
        builder.registerTypeAdapter(String.class, mTolerantStringAdapter);
        builder.registerTypeAdapter(Date.class, mTolerantDateAdapter);
        builder.registerTypeAdapter(PipelineResponse.class, mTolerantPipelineAdapter);
        builder.registerTypeAdapter(PipelineProductResponse.class, mTolerantPipelineProductAdapter);
        return builder.create();
    }
}
