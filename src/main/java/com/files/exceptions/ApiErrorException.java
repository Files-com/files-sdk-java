package com.files.exceptions;

import java.io.IOException;

public class ApiErrorException extends IOException {
  private static final long serialVersionUID = -7605776674523623420L;

  public static ApiErrorException forStatus(int status, String msg) {
    if (status == 400) {
      return new Api400Exception(msg);
    } else if (status == 401) {
      return new ApiAuthenticationException(msg);
    } else if (status == 403) {
      return new Api403Exception(msg);
    } else if (status == 404) {
      return new Api404Exception(msg);
    } else if (status == 409) {
      return new Api409Exception(msg);
    } else if (status == 500) {
      return new Api500Exception(msg);
    } else if (status == 502) {
      return new Api502Exception(msg);
    } else if (status == 503) {
      return new Api503Exception(msg);
    } else if (status == 504) {
      return new Api504Exception(msg);
    } else {
      return new ApiErrorException(status, msg);
    }
  }

  private final int code;
  private final String message;

  private ApiErrorException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "[" + code + "] " + message;
  }

  public static class Api400Exception extends ApiErrorException {
    private static final long serialVersionUID = -4762765896637431151L;

    public Api400Exception() {
      this(null);
    }

    public Api400Exception(String msg) {
      super(400, msg);
    }
  }

  public static class Api403Exception extends ApiErrorException {
    private static final long serialVersionUID = -395830670282395020L;

    public Api403Exception() {
      this(null);
    }

    public Api403Exception(String msg) {
      super(403, msg);
    }
  }

  public static class Api404Exception extends ApiErrorException {
    private static final long serialVersionUID = 4123332401175064868L;

    public Api404Exception() {
      this(null);
    }

    public Api404Exception(String msg) {
      super(404, msg);
    }
  }

  public static class Api409Exception extends ApiErrorException {
    private static final long serialVersionUID = 4123332401175064868L;

    public Api409Exception() {
      this(null);
    }

    public Api409Exception(String msg) {
      super(409, msg);
    }
  }

  public static class Api500Exception extends ApiErrorException {
    private static final long serialVersionUID = -5068814871175222005L;

    public Api500Exception() {
      this(null);
    }

    public Api500Exception(String msg) {
      super(500, msg);
    }
  }

  public static class Api502Exception extends ApiErrorException {
    private static final long serialVersionUID = -5068814871175222005L;

    public Api502Exception() {
      this(null);
    }

    public Api502Exception(String msg) {
      super(502, msg);
    }
  }

  public static class Api503Exception extends ApiErrorException {
    private static final long serialVersionUID = -5068814871175222005L;

    public Api503Exception() {
      this(null);
    }

    public Api503Exception(String msg) {
      super(503, msg);
    }
  }

  public static class Api504Exception extends ApiErrorException {
    private static final long serialVersionUID = -5068814871175222005L;

    public Api504Exception() {
      this(null);
    }

    public Api504Exception(String msg) {
      super(504, msg);
    }
  }

  public static class ApiAuthenticationException extends ApiErrorException {
    private static final long serialVersionUID = -2317455473779675387L;

    public ApiAuthenticationException() {
      this(null);
    }

    public ApiAuthenticationException(String msg) {
      super(401, msg);
    }
  }
}
