package io.mockative

abstract class Spyable(stubsUnitByDefault: Boolean) : Mockable(stubsUnitByDefault = stubsUnitByDefault) {
    protected fun <R> spyBlocking()
}
