import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { login } from "../functions/login";
import Request from "../functions/Request";

export default function Callback() {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {

    const makeReqToServer = async () => {
      const code = searchParams.get("code");
      const response = await Request.get('/auth/oauth-login?code=' + code);
      if (response.data.status) {
        login(response.data.content.jwt, response.data.content.username);
        navigate('/commodities', { replace: true });
      }
    }

    makeReqToServer();
  }, []);

  return (
    <>
    </>
  );
}