package com.api.app.patchImpl;

public interface ObjectPatch {

	<T> T apply(T target) throws ObjectPatchException;
	
}
