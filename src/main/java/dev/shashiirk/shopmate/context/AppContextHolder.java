package dev.shashiirk.shopmate.context;

import dev.shashiirk.shopmate.dto.AppContextDTO;

public final class AppContextHolder {

    private AppContextHolder() {
    }

    private static final ThreadLocal<AppContextDTO> CONTEXT = ThreadLocal.withInitial(
            () -> AppContextDTO.builder().build()
    );

    public static AppContextDTO getContext() {
        return CONTEXT.get();
    }

    public static void setContext(AppContextDTO context) {
        CONTEXT.set(context);
    }

    public static void clearContext() {
        CONTEXT.remove();
    }

}
