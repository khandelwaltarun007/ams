package com.javalabs.exception.common;

public final class ExceptionFactory {
	private ExceptionFactory() {
	}

	public static class InternalServerError {
		private InternalServerError() {
		}

		public static MyCustomException throwInternalSystemErrorException(String attribute, String detail) {
			return new InternalSystemErrorException(attribute, detail);
		}

		/*
		 * public static MyCustomException throwDatabaseNotAvailableException() { return
		 * new DatabaseNotAvailableException(); }
		 * 
		 * public static MyCustomException throwDatabaseTimeoutException() { return new
		 * DatabaseTimeoutException(); }
		 * 
		 * public static MyCustomException throwDatabaseProcessingException(String
		 * errorCode, String detail) { return new DatabaseProcessingException(errorCode,
		 * detail); }
		 * 
		 * public static MyCustomException throwKafkaNotAvailableException() { return
		 * new KafkaNotAvailableException(); }
		 * 
		 * public static MyCustomException throwKafkaTimeoutException() { return new
		 * KafkaTimeoutException(); }
		 * 
		 * public static MyCustomException throwKafkaProcessingException(String
		 * errorCode, String detail) { return new KafkaProcessingException(errorCode,
		 * detail); }
		 * 
		 * public static MyCustomException
		 * throwDependentWebServiceNotAvailableException(String serviceUrl) { return new
		 * DependentWebServiceNotAvailableException(serviceUrl); }
		 * 
		 * public static MyCustomException
		 * throwDependentWebServiceTimeoutException(String serviceUrl) { return new
		 * DependentWebServiceTimeoutException(serviceUrl); }
		 * 
		 * public static MyCustomException
		 * throwDependentWebServiceResponseValidationException(String validationMessage)
		 * { return new
		 * DependentWebServiceResponseValidationException(validationMessage); }
		 * 
		 * public static MyCustomException
		 * throwDependentWebServiceProcessingProblem(String errorReason, String
		 * errorCode, String errorDetail) { return new
		 * DependentWebServiceProcessingProblem(errorReason, errorCode, errorDetail); }
		 * }
		 * 
		 * public static class BadRequest { private BadRequest() { }
		 * 
		 * public static MyCustomException throwNotAcceptableAcceptHeaderException() {
		 * return new NotAcceptableAcceptHeaderException(); }
		 * 
		 * public static MyCustomException
		 * throwNotAcceptableContentTypeHeaderException() { return new
		 * NotAcceptableContentTypeHeaderException(); }
		 * 
		 * public static MyCustomException throwNotAcceptableHeaderException(String
		 * headerName, String currentValue) { return new
		 * NotAcceptableHeaderException(headerName, currentValue); }
		 * 
		 * public static MyCustomException throwPayloadTooLargeException() { return new
		 * PayloadTooLargeException(); }
		 * 
		 * public static MyCustomException throwURITooLongException() { return new
		 * URITooLongException(); }
		 * 
		 * public static MyCustomException throwUnprocessablePayloadException() { return
		 * new UnprocessablePayloadException(); }
		 * 
		 * public static MyCustomException throwUnprocessablePayloadException(String
		 * reason) { return new UnprocessablePayloadException(reason); }
		 * 
		 * public static MyCustomException throwDuplicateIdException() { return new
		 * DuplicateIdException(); }
		 * 
		 * public static MyCustomException throwDuplicateIdAndVersionException() {
		 * return new DuplicateIdAndVersionException(); }
		 * 
		 * public static MyCustomException throwInvalidNumericValueRangeException(String
		 * referenceError, String fieldName, String currentValue, String minValue,
		 * String maxValue) { return new
		 * InvalidNumericValueRangeException(referenceError, fieldName, currentValue,
		 * minValue, maxValue); }
		 * 
		 * public static MyCustomException throwInvalidLengthException(String
		 * referenceError, String fieldName, String currentValue, String minLength,
		 * String maxLength) { return new InvalidLengthException(referenceError,
		 * fieldName, currentValue, minLength, maxLength); }
		 * 
		 * public static MyCustomException throwInvalidRegexException(String
		 * referenceError, String fieldName, String currentValue, String regExp) {
		 * return new InvalidRegexException(referenceError, fieldName, currentValue,
		 * regExp); }
		 * 
		 * public static MyCustomException throwInvalidObjectTypeException(String
		 * referenceError, String fieldName, String currentValue) { return new
		 * InvalidObjectTypeException(referenceError, fieldName, currentValue); }
		 * 
		 * public static MyCustomException throwInvalidDateException(String
		 * referenceError, String fieldName, String currentValue) { return new
		 * InvalidDateException(referenceError, fieldName, currentValue); }
		 * 
		 * public static MyCustomException throwInvalidNumberException(String fieldName,
		 * String currentValue) { return new InvalidNumberException(fieldName,
		 * currentValue); }
		 * 
		 * public static MyCustomException throwInvalidEnumerationException(String
		 * referenceError, String fieldName, String currentValue) { return new
		 * InvalidEnumerationException(referenceError, fieldName, currentValue); }
		 * 
		 * public static MyCustomException throwInvalidIdException(String id) { return
		 * new InvalidIdException(id); }
		 * 
		 * public static MyCustomException throwUnprocessableRequestURIException() {
		 * return new UnprocessableRequestURIException(); }
		 * 
		 * public static MyCustomException throwInvalidPathParameterException(String
		 * urlParameter) { return new InvalidPathParameterException(urlParameter); }
		 * 
		 * public static MyCustomException throwInvalidUrlParameterException(String
		 * urlParameter) { return new InvalidUrlParameterException(urlParameter); }
		 * 
		 * public static MyCustomException throwInvalidSortParameterException() { return
		 * new InvalidSortParameterException(); }
		 * 
		 * public static MyCustomException throwInvalidFieldParameterException() {
		 * return new InvalidFieldParameterException(); }
		 * 
		 * public static MyCustomException throwInvalidPathParameterValueException() {
		 * return new InvalidPathParameterValueException(); }
		 * 
		 * public static MyCustomException throwMissingRequiredFieldException(String
		 * referenceError, String[] missingAttributes) { return new
		 * MissingRequiredFieldException(referenceError, missingAttributes); }
		 * 
		 * public static MyCustomException throwAdditionalFieldException(String
		 * referenceError, String... additionalAttributes) { return new
		 * AdditionalFieldException(referenceError, additionalAttributes); }
		 * 
		 * public static MyCustomException throwInvalidBooleanException(String
		 * fieldName, String currentValue) { return new
		 * InvalidBooleanException(fieldName, currentValue); }
		 * 
		 * public static MyCustomException throwOtherBadRequestException(String reason)
		 * { return new OtherBadRequestException(reason); }
		 * 
		 * public static MyCustomException throwReferenceNotFoundException(String
		 * fieldName, String id) { return new ReferenceNotFoundException(fieldName, id);
		 * }
		 * 
		 * public static MyCustomException throwInconsistentDataException(String attr,
		 * String value) { return new InconsistentDataException(attr, value); }
		 * 
		 * public static MyCustomException throwInconsistentDataException(String...
		 * valueAndAttributes) { return new
		 * InconsistentDataException(valueAndAttributes); }
		 * 
		 * public static MyCustomException throwBlankMandatoryFieldException(String...
		 * missingAttributes) { return new
		 * BlankMandatoryFieldException(missingAttributes); }
		 * 
		 * public static MyCustomException
		 * throwNullableOrNotBlankMandatoryFieldException(String... missingAttributes) {
		 * return new NullableOrNotBlankMandatoryFieldException(missingAttributes); }
		 * 
		 * public static MyCustomException throwInvalidTokenClaimException() { return
		 * new InvalidTokenClaimException(); } }
		 * 
		 * public static class MethodNotAllowed { private MethodNotAllowed() { }
		 * 
		 * public static MyCustomException throwMethodNotAllowedException(String method,
		 * String resource) { return new MethodNotAllowedException(method, resource); }
		 * }
		 * 
		 * public static class NotFound { private NotFound() { }
		 * 
		 * public static MyCustomException throwNotFoundException() { return new
		 * NotFoundException(); } }
		 * 
		 * public static class Conflict { private Conflict() { }
		 * 
		 * public static MyCustomException throwConflictException() { return new
		 * ConflictException(); } }
		 * 
		 * public static class Unauthorized { private Unauthorized() { }
		 * 
		 * public static MyCustomException throwUnauthorizedException() { return new
		 * UnauthorizedException(); } }
		 * 
		 * public static class NotAcceptable { private NotAcceptable() { }
		 * 
		 * public static MyCustomException throwNotAcceptableException(String reason) {
		 * return new NotAcceptableException(reason); } }
		 * 
		 * public static class Forbidden { private Forbidden() { }
		 * 
		 * public static MyCustomException throwForbiddenException() { return new
		 * ForbiddenException(); } }
		 * 
		 * public static class PreconditionFailed { private PreconditionFailed() { }
		 * 
		 * public static MyCustomException throwInvalidIfMatchHeaderException(String
		 * name) { return new InvalidIfMatchHeaderException(name); } }
		 * 
		 * public static class PreconditionRequired { private PreconditionRequired() { }
		 * 
		 * public static MyCustomException throwMissingIfMatchHeaderException() { return
		 * new MissingIfMatchHeaderException(); }
		 */
	}
}
