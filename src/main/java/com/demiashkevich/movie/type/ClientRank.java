package com.demiashkevich.movie.type;

public enum ClientRank {
    BAFTA("BAFTA"),
    GOLDEN_GLOBES("Golden_globes"),
    OSCAR("Oscar");

    private String nameRank;

    ClientRank(String nameRank){
        this.nameRank = nameRank;
    }

    public String getNameRank() {
        return nameRank;
    }
}
