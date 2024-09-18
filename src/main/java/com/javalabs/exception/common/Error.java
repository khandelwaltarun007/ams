package com.javalabs.exception.common;

import java.io.Serializable;

public class Error implements Serializable {
    private String code;
    private String reason;
    private String message;
    private String status;
    private String referenceError;
    private String baseType;
    private String schemaLocation;
    private String type;

    Error(final String code, final String reason, final String message, final String status, final String referenceError, final String baseType, final String schemaLocation, final String type) {
        this.code = code;
        this.reason = reason;
        this.message = message;
        this.status = status;
        this.referenceError = referenceError;
        this.baseType = baseType;
        this.schemaLocation = schemaLocation;
        this.type = type;
    }

    public static ErrorBuilder builder() {
        return new ErrorBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public String getReason() {
        return this.reason;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public String getReferenceError() {
        return this.referenceError;
    }

    public String getBaseType() {
        return this.baseType;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public static class ErrorBuilder {
        private String code;
        private String reason;
        private String message;
        private String status;
        private String referenceError;
        private String baseType;
        private String schemaLocation;
        private String type;

        ErrorBuilder() {
        }

        public ErrorBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public ErrorBuilder reason(final String reason) {
            this.reason = reason;
            return this;
        }

        public ErrorBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ErrorBuilder status(final String status) {
            this.status = status;
            return this;
        }

        public ErrorBuilder referenceError(final String referenceError) {
            this.referenceError = referenceError;
            return this;
        }

        public ErrorBuilder baseType(final String baseType) {
            this.baseType = baseType;
            return this;
        }

        public ErrorBuilder schemaLocation(final String schemaLocation) {
            this.schemaLocation = schemaLocation;
            return this;
        }

        public ErrorBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public Error build() {
            return new Error(this.code, this.reason, this.message, this.status, this.referenceError, this.baseType, this.schemaLocation, this.type);
        }

        public String toString() {
            return "Error.ErrorBuilder(code=" + this.code + ", reason=" + this.reason + ", message=" + this.message + ", status=" + this.status + ", referenceError=" + this.referenceError + ", baseType=" + this.baseType + ", schemaLocation=" + this.schemaLocation + ", type=" + this.type + ")";
        }
    }
}
