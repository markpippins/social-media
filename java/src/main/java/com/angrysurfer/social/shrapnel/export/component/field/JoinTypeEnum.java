package com.angrysurfer.social.shrapnel.export.component.field;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum JoinTypeEnum {

	EQUALS( 1 );

	private int code;

	JoinTypeEnum(int code ) {
		this.code = code;
	}

	public static JoinTypeEnum from(String name ) throws IllegalArgumentException {

		JoinTypeEnum result = Arrays.asList( JoinTypeEnum.values( ) ).stream( ).filter(type -> type.name( ).equals( name ) ).findFirst( )
				.orElseThrow( ( ) -> new IllegalArgumentException(
						"\nUnknown name '" + name + "', Allowed names are "
								+ String.join( ", ", Arrays.asList( JoinTypeEnum.values( ) ).stream( ).map( (JoinTypeEnum type ) -> {
							return String.join( name, "'", "'" );
						} ).collect( Collectors.toList( ) ) ) ) );

		return result;
	}

	public static JoinTypeEnum from(int code ) throws IllegalArgumentException {
		
		JoinTypeEnum result = Arrays.asList( JoinTypeEnum.values( ) ).stream( ).filter(type -> type.getCode( ) == code ).findFirst( )
				.orElseThrow( ( ) -> new IllegalArgumentException(
						"\nUnknown name '" + code + "', Allowed codes are "
								+ String.join( ", ", Arrays.asList( JoinTypeEnum.values( ) ).stream( ).map( (JoinTypeEnum type ) -> {
							return String.join( Integer.toString( code ), "'", "'" );
						} ).collect( Collectors.toList( ) ) ) ) );

		return result;
	}

	public int getCode( ) {
		return code;
	}
}
